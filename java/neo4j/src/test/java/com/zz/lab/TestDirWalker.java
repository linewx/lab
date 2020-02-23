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

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDirWalker {
    @Autowired
    private PersonRepository personRepository;

    public static class Finder implements FileVisitor<Path> {
        private List<String> found = new ArrayList<>();

        public List<String> getFound() {
            return found;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.endsWith("pom.xml")) {
                found.add(file.toString());
            }

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

    @Test
    public void testWalker() {
        Finder finder = new Finder();
        try {
            Files.walkFileTree(Paths.get("/Users/luganlin/git/itsma-x"), finder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(finder.found);
    }

}
