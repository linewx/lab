package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.parser.Finder;
import com.zz.lab.neo4j.parser.PomParser;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
public class DepAnalyzer {

    @Autowired
    private ArtifactService artifactService;

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private List<MergeStrategy> mergeStrategies;

    @Autowired
    private ChildMergeStrategy childMergeStrategy;

    @Autowired
    private ParentMergeStrategy parentMergeStrategy;

    @Autowired
    private GroupMergeStrategy groupMergeStrategy;

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
    public void bfs() {
        Artifact artifact = Artifact.builder()
                .artifactId("group")
                .groupId("com.hp.maas.platform.services.tenant-settings")
                .build();

        LinkedHashSet<Artifact> results = artifactService.bfs(artifact);
        for (Artifact one: results) {
            System.out.println(one);
        }
        System.out.println(results.size());

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

    @Test
    public void applyChildMerge() {
        childMergeStrategy.merge();
    }

    @Test
    public void applyParentMerge() {
        parentMergeStrategy.merge();
    }

    @Test
    public void applyGroupMerge() {
        groupMergeStrategy.merge();
    }

    @Test
    public void reviseArtifacts() {
        //make platform webapp out of runtime group
        Artifact artifact = artifactRepository.findOneByArtifactIdAndGroupId("platform-webapp", "com.hp.maas.platform.runtime");
        artifact.setGroupId("com.hp.maas.platform.runtime.platform-webapp.revised");
        artifactRepository.save(artifact);

        artifact = artifactRepository.findOneByArtifactIdAndGroupId("new-deployer", "com.hp.maas.platform.runtime");
        artifact.setGroupId("com.hp.maas.platform.runtime.new-deployer.revised");
        artifactRepository.save(artifact);
    }

    @Test
    public void analyze() throws Exception{
        initGraph();
        reviseArtifacts();
        applyMerge();
    }
}
