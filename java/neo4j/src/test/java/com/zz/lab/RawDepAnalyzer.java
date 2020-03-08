package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.RawArtifact;
import com.zz.lab.neo4j.parser.Finder;
import com.zz.lab.neo4j.parser.PomParser;
import com.zz.lab.neo4j.repo.RawArtifactRepository;
import com.zz.lab.neo4j.service.RawArtifactService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
@Slf4j
public class RawDepAnalyzer {

    @Autowired
    private RawArtifactService artifactService;

    @Autowired
    private RawArtifactRepository artifactRepository;


    @Test
    public void bfs() {
        RawArtifact artifact = RawArtifact.builder()
                .artifactId("group")
                .groupId("com.hp.maas.platform.services.tenant-settings")
                .build();

        LinkedHashSet<RawArtifact> results = artifactService.bfs(artifact);
        for (RawArtifact one : results) {
            System.out.println(one);
        }
        System.out.println(results.size());

    }

    @Test
    public void directDeps() {
        String artifactId = "theme-settings-impl";
        RawArtifact artifact = artifactRepository.findOneByArtifactId(artifactId);

        Collection<RawArtifact> artifactList = artifactRepository.getChildren(artifact.getArtifactId(), artifact.getGroupId());
        artifactList.forEach(x -> System.out.println(x.toString()));
        System.out.println("total count: " + artifactList.size());
    }


    @Test
    public void initGraph() throws Exception {
        artifactService.deleteAll();
        //identify a small group
        ///Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/
        String rootPath = "/Users/luganlin/git/itsma-x/paas";
        //String rootPath = "/Users/luganlin/git/itsma-x/paas/platform/core/metadata/";

        //get all pom.xml
        Finder finder = new Finder();

        Files.walkFileTree(Paths.get(rootPath), finder);

        List<String> pomFiles = finder.getFound();

        //find the dependencies
        for (String onePomFile : pomFiles) {
            PomParser pomParser = new PomParser(onePomFile, "com.hp");

            RawArtifact basicArtifact = new RawArtifact();
            BeanUtils.copyProperties(pomParser.parseBasic(), basicArtifact);

            List<RawArtifact> deps = new ArrayList<>();

            pomParser.parseDependencies().forEach(x -> {
                RawArtifact rawArtifact = new RawArtifact();
                BeanUtils.copyProperties(x, rawArtifact);
                deps.add(rawArtifact);
            });

            for (RawArtifact one : deps) {
                //refine the data model for artifacts

                //add them all into neo4j
                artifactService.addRelation(basicArtifact, one);
            }
        }
    }

    @Test
    public void findAllModules() {

        String suffixFilter = "api";
        System.out.println("list all " + suffixFilter);
        List<RawArtifact> results = new ArrayList<>();
        for (RawArtifact rawArtifact : artifactRepository.findAll()) {
            if (rawArtifact.getArtifactId().endsWith(suffixFilter)) {
                results.add(rawArtifact);
            }
        }

        results.forEach(x -> System.out.println(x.toString()));
        System.out.println("total " + suffixFilter + " is:" + results.size());
    }

    @Test
    public void analyzeArtifacts() {
        List<String> suffixes = Arrays.asList("api", "ws", "impl", "client");


        Map<String, List<RawArtifact>> results = new HashMap<>();
        results.put("others", new ArrayList<>());

        Iterable<RawArtifact> allArtifacts = artifactRepository.findAll();
        allArtifacts.forEach(x -> {
            String artifactId = x.getArtifactId();
            boolean found = false;
            for (String oneSuffix: suffixes) {
                if (artifactId.endsWith(oneSuffix)) {
                    if (!results.containsKey(oneSuffix)) {
                        results.put(oneSuffix, new ArrayList<>());
                    }
                    results.get(oneSuffix).add(x);
                    found = true;
                }
            }

            if (!found) {
                results.get("others").add(x);
            }

        });

        for (String suffix: suffixes) {
            System.out.println(suffix + ":" + results.get(suffix).size());
        }

        System.out.println("others:" + results.get("others").size());

        results.get("others").forEach(x -> System.out.println(x.toString()));
    }

    @Test
    public void analyzeDegree() {
       List<Map<String, Object>> results = artifactRepository.findAllDeps();

       results.forEach(x -> {
           RawArtifact rawArtifact = (RawArtifact)x.get("artifact");
           Long indegree = (Long)x.get("indegree");
           Long outdegree = (Long)x.get("outdegree");

           //System.out.println("artifact: " +  rawArtifact.toString() + ",indegree: " + indegree + ",outdegree: " + outdegree);
           System.out.println(rawArtifact.toString() + " " + indegree + " " + outdegree + " "+ guessType(rawArtifact.getArtifactId()));
       });
    }

    private String guessType(String artifactId) {
        List<String> suffixes = Arrays.asList("api", "ws", "impl", "client");
        for (String suffix: suffixes) {
            if (artifactId.endsWith(suffix)) {
                return suffix;
            }
        }
        return "others";
    }

    @Test
    public void findAllDeps() {
        String moduleName = "common_utils";

        List<String> suffixes = Arrays.asList("api", "ws", "impl", "client");
        for (String suffix : suffixes) {
            analyzeOneModule(moduleName + "-" + suffix);
        }

        analyzeOneModule(moduleName);
    }

    private void analyzeOneModule(String artifactId) {

        try {
            System.out.println("###### start analyze the module " + artifactId + " ######");

            RawArtifact artifact = artifactRepository.findOneByArtifactId(artifactId);

            // parents
            System.out.println("\n*** list all parents for the module " + artifactId + " ***");
            Collection<RawArtifact> parents = artifactRepository.getAllParents(artifact.getArtifactId(), artifact.getGroupId());
            for (RawArtifact parent : parents) {
                System.out.println(parent.toString());
            }


            //children
            System.out.println("\n*** list all children for the module " + artifactId + " ***");
            Collection<RawArtifact> children = artifactRepository.getChildren(artifact.getArtifactId(), artifact.getGroupId());
            for (RawArtifact child : children) {
                System.out.println(child.toString());
            }

            System.out.println("###### end analyze the module " + artifactId + " ######\n\n\n\n\n");


        } catch (Exception e) {
            log.warn("!!! the artifact " + artifactId + " not found");
        }
    }

}

