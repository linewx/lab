package com.zz.lab.pom;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PomDepParser {
    private static Dependency parseDepNode(Node depNode) {
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
    public static List<Dependency> parse(String path) {
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

    private static List<Dependency> parseDependenciesNode(Node oneDeps) {
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
