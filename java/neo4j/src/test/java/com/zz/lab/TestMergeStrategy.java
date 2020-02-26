package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.*;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.repo.PersonRepository;
import com.zz.lab.neo4j.service.ArtifactService;
import com.zz.lab.neo4j.service.ChildMergeStrategy;
import com.zz.lab.neo4j.service.MergeStrategy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
@Slf4j
public class TestMergeStrategy {
    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private ArtifactService artifactService;


    @Test
    public void testArtifact() {
        List<Artifact> artifacts = artifactRepository.findAllByGroupId("com.zz.lab");
        artifactRepository.deleteAll(artifacts);

        //test cases: example1/example2 -> example3 -> example4/example5

        Artifact example1 = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("example1")
                .build();

        Artifact example2 = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("example2")
                .build();

        Artifact example3 = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("example3")
                .build();

        Artifact example4 = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("example4")
                .build();

        Artifact example5 = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("example5")
                .build();

        artifactService.addRelation(example1, example3);
        artifactService.addRelation(example2, example3);
        artifactService.addRelation(example3, example4);
        artifactService.addRelation(example3, example5);

        Collection<Artifact> nodesToMergeToChild = artifactRepository.getAllArtifactByDeps(1, 0);
        List<Artifact> artifactsToMergeToChild = nodesToMergeToChild.stream().filter(x -> x.getGroupId().equals("com.zz.lab")).collect(Collectors.toList());

        Assert.assertEquals(artifactsToMergeToChild.size(), 2);
        System.out.println(artifactsToMergeToChild.get(0).getArtifactId());
        System.out.println(artifactsToMergeToChild.get(1).getArtifactId());

        for (Artifact artifact : artifactsToMergeToChild) {
            //get the child
            Collection<Artifact> children = artifactRepository.getChildren(artifact.getArtifactId(), artifact.getGroupId());
            Assert.assertEquals(children.size() , 1);
            Artifact child = children.iterator().next();
            log.info("start merge node " + artifact.toString() + " to" + child.toString());
            MergeStrategy mergeStrategy = ChildMergeStrategy.builder()
                    .node(artifact)
                    .childNode(child)
                    .artifactRepository(artifactRepository)
                    .build();

            mergeStrategy.merge();

            log.info("end merge node " + artifact.toString() + " to" + child.toString());
        }
        Assert.assertEquals(3, artifactRepository.findAllByGroupId("com.zz.lab").size());

        Artifact theExample3 = artifactRepository.findAllByArtifactId("example3").iterator().next();
        List<ArtifactPath> mergePaths = theExample3.getMergePaths();

        Assert.assertEquals(mergePaths.size(), 2);
        log.info(mergePaths.get(0).toString());
        log.info(mergePaths.get(1).toString());

    }





}
