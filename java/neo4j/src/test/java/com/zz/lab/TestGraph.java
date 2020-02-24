package com.zz.lab;

import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.entity.Person;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import com.zz.lab.neo4j.repo.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class TestGraph {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private ArtifactRepository artifactRepository;


    private void addRelation(Artifact foo, Artifact bar) {
        Artifact theFoo = artifactRepository.findByArtifactIdAndGroupId(foo.getArtifactId(), foo.getGroupId());
        if (theFoo == null) {
            theFoo = artifactRepository.save(foo);
        }

        Artifact theBar = artifactRepository.findByArtifactIdAndGroupId(bar.getArtifactId(), bar.getGroupId());
        if (theBar == null) {
            theBar = artifactRepository.save(bar);
        }

        theFoo.depends(theBar);
        artifactRepository.save(theFoo);
    }

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

        addRelation(foo, bar);
        addRelation(foo, baz);
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



}
