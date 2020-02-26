package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.parser.PomParser;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.repo.PersonRepository;
import com.zz.lab.neo4j.parser.Finder;
import com.zz.lab.neo4j.service.ArtifactService;
import com.zz.lab.neo4j.service.MergeStrategy;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedHashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
public class DepAnalyzer {

    @Autowired
    private ArtifactService artifactService;

    @Autowired
    private List<MergeStrategy> mergeStrategies;

    @Test
    public void testWalker() {
        Finder finder = new Finder();
        try {
            Files.walkFileTree(Paths.get("/Users/luganlin/git/itsma-x"), finder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(finder.getFound());
    }

    @Test
    public void testDfs() {
        Artifact artifact = Artifact.builder()
                .artifactId("tenant-settings-impl")
                .groupId("com.hp.maas.platform.services.tenant-settings")
                .build();

        LinkedHashSet<Artifact> results = artifactService.bfs(artifact);
        for (Artifact one: results) {
            System.out.println(one);
        }

    }


    @Test
    public void initGraph() throws Exception{
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

            Artifact basicArtifact = pomParser.parseBasic();

            List<Artifact> deps = pomParser.parseDependencies();

            for (Artifact one: deps) {
                //refine the data model for artifacts

                //add them all into neo4j
                artifactService.addRelation(basicArtifact, one);
            }
        }
    }

    @Test
    public void applyMerge() {
        mergeStrategies.forEach(MergeStrategy::merge);
    }

}
