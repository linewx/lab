package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.*;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.repo.PersonRepository;
import com.zz.lab.neo4j.service.ArtifactService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
@Slf4j
public class TestGraph {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ArtifactRepository artifactRepository;

    @Autowired
    private ArtifactService artifactService;


    @Test
    public void testArtifact() {
        Artifact foo = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("foo")
                .build();

        Artifact bar = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("bar")
                .build();

        Artifact baz = Artifact.builder()
                .groupId("com.zz.lab")
                .artifactId("baz")
                .build();

        artifactService.addRelation(foo, bar);
        artifactService.addRelation(foo, baz);
    }




    @Test
    public void testGraph() {
        personRepository.deleteAll();

        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");

        List<Person> team = Arrays.asList(greg, roy, craig);

        log.info("Before linking up with Neo4j...");

        team.stream().forEach(person -> log.info("\t" + person.toString()));

        personRepository.save(greg);
        personRepository.save(roy);
        personRepository.save(craig);

        greg = personRepository.findByName(greg.getName());
        greg.worksWith(roy);
        greg.worksWith(craig);
        personRepository.save(greg);

        roy = personRepository.findByName(roy.getName());
        roy.worksWith(craig);
        // We already know that roy works with greg
        personRepository.save(roy);

        // We already know craig works with roy and greg

        log.info("Lookup each person by name...");
        team.stream().forEach(person -> log.info(
                "\t" + personRepository.findByName(person.getName()).toString()));

    }

    @Test
    public void testCypher() {
        Collection<Artifact> artifacts = artifactRepository.getAllArtifactByDeps(1, 0);
        System.out.println(artifacts);

        Collection<Artifact> parent = artifactRepository.getAllParents("metadata-impl", "com.hp.maas.platform.core.metadata");
        System.out.println(parent);

        Collection<Artifact> children = artifactRepository.getChildren("metadata-impl", "com.hp.maas.platform.core.metadata");
        System.out.println(children);

        System.out.println(artifactRepository.count());
    }

    @Test
    public void testPathType() {
        Assert.assertEquals(PathType.valueOf("CHILD"), PathType.CHILD);
        Assert.assertEquals("CHILD", PathType.CHILD.toString());
    }

    @Test
    public void testArtifactPathConverter() {
        //clear test case
        Collection<Artifact> artifacts =
        artifactRepository.findAllByArtifactIdAndGroupId("foo", "com.example");

        artifactRepository.deleteAll(artifacts);

        ArtifactMergePathConverter converter = new ArtifactMergePathConverter();

        ArtifactPathNode artifactPathNode = ArtifactPathNode.builder()
                .groupId("com.hp.platform.metadata")
                .artifactId("metadata-impl")
                .pathType(PathType.GROUP)
                .build();

        ArtifactPathNode artifactPathNode2 = ArtifactPathNode.builder()
                .groupId("com.hp.platform.metadata")
                .artifactId("metadata-api")
                .pathType(PathType.PARENT)
                .build();

        ArtifactPathNode artifactPathNode3 = ArtifactPathNode.builder()
                .groupId("com.hp.platform.tenant")
                .artifactId("tenant-impl")
                .pathType(PathType.PARENT)
                .build();

        ArtifactPathNode artifactPathNode4 = ArtifactPathNode.builder()
                .groupId("com.hp.platform.tenant")
                .artifactId("tenant-api")
                .pathType(PathType.CHILD)
                .build();

        ArtifactPath artifactPath =  ArtifactPath.builder()
                .path(Arrays.asList(artifactPathNode, artifactPathNode2))
                .build();

        ArtifactPath artifactPath2 =  ArtifactPath.builder()
                .path(Arrays.asList(artifactPathNode3, artifactPathNode4))
                .build();

        List<ArtifactPath> paths = Arrays.asList(artifactPath, artifactPath2);

        String pathString = converter.toGraphProperty(paths);
        System.out.println("######### path string ####### ");
        System.out.println(pathString);

        List<ArtifactPath> artifactPaths = converter.toEntityAttribute(pathString);

        System.out.println("########## artifact paths #######");
        artifactPaths.forEach(x -> System.out.println(x.toString()));

        Artifact oneArtifact = Artifact.builder()
                .artifactId("foo")
                .groupId("com.example")
                .mergePaths(paths)
                .build();

        artifactRepository.save(oneArtifact);

        Artifact twoArtifact = artifactRepository.findOneByArtifactIdAndGroupId("foo", "com.example");
        Assert.assertEquals(twoArtifact.getMergePaths().size(), 2);


    }

    @Test
    public void testGroupByGroupId() {
        List<Map<String, Object>> results =  artifactRepository.findGroupByGroupId();
        results.forEach(x -> {
            System.out.println(x);
        });
    }

    @Test
    public void testFindDepth() {
        Artifact artifact = artifactRepository.findOneByArtifactIdAndGroupId("metadata-impl", "com.hp.maas.platform.core.metadata", 3);
        System.out.println(artifact.getDeps());
    }
}
