package com.zz.lab;

import com.zz.lab.entity.Person;
import com.zz.lab.pom.Artifact;
import com.zz.lab.pom.PomParser;
import com.zz.lab.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPomParser {
    @Autowired
    private PersonRepository personRepository;

    @Test
    public void testParsePom() {
        String parentPom = "/Users/luganlin/git/itsma-x/pom.xml";
        String tenantPom = "/Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/tenant-settings-api/pom.xml";


        List<Artifact> parentDeps = new PomParser(parentPom).parseDependencies();
        System.out.println(parentDeps.size());


        List<Artifact> tenantDeps = new PomParser(tenantPom).parseDependencies();
        System.out.println(tenantDeps.size());

    }

    @Test
    public void testGraph() {
        personRepository.deleteAll();

        Person greg = new Person("Greg");
        Person roy = new Person("Roy");
        Person craig = new Person("Craig");

        personRepository.save(greg);
        personRepository.save(roy);
        personRepository.save(craig);

        Person person = personRepository.findByName("Greg");

        System.out.println(person);
    }

}
