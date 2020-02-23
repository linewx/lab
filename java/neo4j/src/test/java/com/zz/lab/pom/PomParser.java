package com.zz.lab.pom;

import com.zz.lab.entity.Artifact;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PomParser {
    private String path;
    private Document doc;
    private String groupPrefixFilter = null;


    public PomParser(String path) {
        Document doc = null;
        this.path = path;
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.doc = doc;
    }

    public PomParser(String path, String groupPrefixFilter) {
        this(path);
        this.groupPrefixFilter = groupPrefixFilter;
    }

    private Artifact parseDepNode(Node depNode) {
        Artifact dependency = new Artifact();

        NodeList nodes = depNode.getChildNodes();
        for (int i=0; i<nodes.getLength(); i++) {
            Node oneNode = nodes.item(i);
            String name = oneNode.getNodeName();
            if ("artifactId".equals(name)) {
                dependency.setArtifactId(oneNode.getTextContent());
            }
            if ("groupId".equals(name)) {
                if (this.groupPrefixFilter != null && !oneNode.getTextContent().startsWith(this.groupPrefixFilter)) {
                    return null;
                }
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

    public  Artifact parseBasic() {
        Artifact artifact = new Artifact();
        NodeList basicNodeList = this.doc.getDocumentElement().getChildNodes();
        for (int index =0 ;index <  basicNodeList.getLength(); index++) {
            Node theNode = basicNodeList.item(index);
            String nodeName = theNode.getNodeName();
            if ("artifactId".equals(nodeName)) {
                artifact.setArtifactId(theNode.getTextContent());
            }
            if ("groupId".equals(nodeName)) {
                artifact.setGroupId(theNode.getTextContent());
            }
            if ("version".equals(nodeName)) {
                artifact.setVersion(theNode.getTextContent());
            }
            if ("packaging".equals(nodeName)) {
                artifact.setPacking(theNode.getTextContent());
            }
        }

        return artifact;

    }

    public  List<Artifact> parseDependencies() {
        List<Artifact> deps = new ArrayList<>();
        try {

            System.out.println("Root element :" + this.doc.getDocumentElement().getNodeName());
            NodeList depList = doc.getElementsByTagName("dependencies");

            for (int i=0; i<depList.getLength(); i++) {
                Node oneDeps = depList.item(i);

                List<Artifact> theDepList = parseDependenciesNode(oneDeps);
                deps.addAll(theDepList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return deps;
    }

    private  List<Artifact> parseDependenciesNode(Node oneDeps) {
        List<Artifact> dependencyList = new ArrayList<>();
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
                Artifact dependency = parseDepNode(deplist.item(i));
                if (dependency != null) {
                    dependencyList.add(dependency);
                }
            }
        }

        return dependencyList;
    }
}
