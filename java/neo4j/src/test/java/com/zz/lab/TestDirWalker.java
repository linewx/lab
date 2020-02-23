package com.zz.lab;

import com.zz.lab.entity.Artifact;
import com.zz.lab.pom.PomParser;
import com.zz.lab.repo.PersonRepository;
import com.zz.lab.utils.Finder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDirWalker {
    @Autowired
    private PersonRepository personRepository;


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
    public void testDepAnalyze() throws Exception{
        //identify a small group
        ///Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/
        String rootPath = "/Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/";

        //get all pom.xml
        Finder finder = new Finder();

        Files.walkFileTree(Paths.get(rootPath), finder);

        List<String> pomFiles = finder.getFound();

        //find the dependencies
        for (String onePomFile : pomFiles) {
            PomParser pomParser = new PomParser(onePomFile);

            Artifact basicArttifact = pomParser.parseBasic();

            List<Artifact> deps = pomParser.parseDependencies();

            //refine the data model for artifacts

            //add them all into neo4j

        }



    }

}
