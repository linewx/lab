package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.*;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.repo.PersonRepository;
import com.zz.lab.neo4j.service.*;
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

    @Autowired
    private ChildMergeStrategy childMergeStrategy;

    @Autowired
    private ParentMergeStrategy parentMergeStrategy;


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

        //apply child merge strategy
        childMergeStrategy.merge(MergeFilter.builder().groupId("com.zz.lab").build());

        Assert.assertEquals(3, artifactRepository.findAllByGroupId("com.zz.lab").size());

        Artifact theExample3 = artifactRepository.findAllByArtifactId("example3").iterator().next();

        List<ArtifactPath> mergePaths = theExample3.getMergePaths();

        Assert.assertEquals(mergePaths.size(), 2);
        log.info(mergePaths.get(0).toString());
        log.info(mergePaths.get(1).toString());


        parentMergeStrategy.merge(MergeFilter.builder().groupId("com.zz.lab").build());
        Assert.assertEquals(1, artifactRepository.findAllByGroupId("com.zz.lab").size());

        theExample3 = artifactRepository.findAllByArtifactId("example3").iterator().next();
        mergePaths = theExample3.getMergePaths();

        Assert.assertEquals(mergePaths.size(), 4);
        log.info(mergePaths.get(0).toString());
        log.info(mergePaths.get(1).toString());
        log.info(mergePaths.get(2).toString());
        log.info(mergePaths.get(3).toString());

    }
}
