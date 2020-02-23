package com.zz.lab;

import lombok.Data;
import lombok.ToString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPomParser {
    @Test
    public void testParsePom() {
        String path = "/Users/luganlin/git/itsma-x/pom.xml";
        String apath = "/Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/tenant-settings-api/pom.xml";

        System.out.println(parsePomDep(apath));
    }

    //Users/luganlin/git/itsma-x/pom.xml
    //Users/luganlin/git/itsma-x/paas/platform/services/tenant-settings/tenant-settings-api/pom.xml
    @Data
    @ToString
    public static class Dependency {
        String groupId;
        String artifactId;
        String version;
        String scope;
    }

    public Dependency parseDepNode(Node depNode) {
        Dependency dependency = new Dependency();

        NodeList nodes = depNode.getChildNodes();
        for (int i=0; i<nodes.getLength(); i++) {
            Node oneNode = nodes.item(i);
            String name = oneNode.getNodeName();
            if ("artifactId".equals(name)) {
                dependency.setArtifactId(oneNode.getTextContent());
            }
            if ("groupId".equals(name)) {
                dependency.setGroupId(oneNode.getTextContent());
            }
            if ("version".equals(name)) {
                dependency.setVersion(oneNode.getTextContent());
            }
            if ("scope".equals(name)) {
                dependency.setScope(oneNode.getTextContent());
            }
        }

        return dependency;
    }
    public List<Dependency> parsePomDep(String path) {
        List<Dependency> deps = new ArrayList<>();
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList depList = doc.getElementsByTagName("dependencies");

            for (int i=0; i<depList.getLength(); i++) {
                Node oneDeps = depList.item(i);

                List<Dependency> theDepList = parseDependenciesNode(oneDeps);
                deps.addAll(theDepList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return deps;
    }

    private List<Dependency> parseDependenciesNode(Node oneDeps) {
        List<Dependency> dependencyList = new ArrayList<>();
        // ignore node with dependencyManagement parent node
        Node parentNode = oneDeps.getParentNode();
        if ("dependencyManagement".equals(parentNode.getNodeName())) {
            return dependencyList;
        }else {
            NodeList deplist =  oneDeps.getChildNodes();
            for (int i=0; i<deplist.getLength();i++) {
                Node subNode = deplist.item(i);
                if (!subNode.getNodeName().equals("dependency")) {
                    continue;
                }
                Dependency dependency = parseDepNode(deplist.item(i));
                dependencyList.add(dependency);
            }
        }

        return dependencyList;
    }
}
