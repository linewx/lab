package com.zz.lab;

import com.zz.lab.neo4j.DummyApplication;
import com.zz.lab.neo4j.entity.Person;
import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.parser.PomParser;
import com.zz.lab.neo4j.repo.PersonRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DummyApplication.class)
public class TestPomParser {

    @Test
    public void testParsePom() {
        String parentPom = "/Users/luganlin/git/itsma-x/pom.xml";
        //String tenantPom = "/Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/tenant-settings-api/pom.xml";
        String tenantPom = "/Users/luganlin/git/itsma-x/paas/platform/services/service-locator/lookup/pom.xml";

        System.out.println("--------- parent pom ----------");
        List<Artifact> parentDeps = new PomParser(parentPom).parseDependencies();
        System.out.println(parentDeps.size());
        Artifact parentBasic = new PomParser(parentPom).parseBasic();
        System.out.println(parentBasic);


        System.out.println("--------- tenant pom ----------");
        List<Artifact> tenantDeps = new PomParser(tenantPom).parseDependencies();
        System.out.println(tenantDeps.size());
        Artifact tenantBasic = new PomParser(tenantPom).parseBasic();
        System.out.println(tenantBasic);


    }

}
