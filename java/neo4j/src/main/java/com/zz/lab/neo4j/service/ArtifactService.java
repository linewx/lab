package com.zz.lab.neo4j.service;


import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArtifactService {
    @Autowired
    private ArtifactRepository artifactRepository;

    @Transactional
    public void addRelation(Artifact foo, Artifact bar) {
        Artifact theFoo = artifactRepository.findOneByArtifactIdAndGroupId(foo.getArtifactId(), foo.getGroupId());
        if (theFoo == null) {
            theFoo = artifactRepository.save(foo);
        }

        Artifact theBar = artifactRepository.findOneByArtifactIdAndGroupId(bar.getArtifactId(), bar.getGroupId());
        if (theBar == null) {
            theBar = artifactRepository.save(bar);
        }

        theFoo.depends(theBar);
        artifactRepository.save(theFoo);
    }

    public void deleteAll() {
        artifactRepository.deleteAll();
    }

    private void addPath(HashMap<Artifact,  List<Artifact>> visitedPath, Artifact parent, Artifact node) {
        if (parent == null) {
            visitedPath.put(node, new ArrayList<>());
        }else {
            if (!visitedPath.containsKey(parent)) {
                log.error("error to insert node" + node.toString());
                throw new RuntimeException("parent node : " + parent.toString() + " should be visited");
            }
            List<Artifact> newPath = new ArrayList<>(visitedPath.get(parent));
            newPath.add(parent);
            visitedPath.put(node, newPath);
        }

    }
    public LinkedHashSet<Artifact> bfs(Artifact theNode) {
        Artifact startNode = artifactRepository.findOneByArtifactIdAndGroupId(theNode.getArtifactId(), theNode.getGroupId());

        LinkedHashSet<Artifact> visited = new LinkedHashSet<>();
        LinkedHashMap<Artifact, List<Artifact>> visitedPath = new LinkedHashMap<>();
        Map<Artifact, Artifact> parentCache = new HashMap<>();


        Queue<Artifact> visitingQueue = new ArrayDeque<>();
        visitingQueue.add(startNode);
        addPath(visitedPath, null, startNode);


        while (visitingQueue.size() != 0) {
            Artifact theArtifact = visitingQueue.poll();

            if (visited.contains(theArtifact)) {
                //visisted
                continue;
            } else {
                // not visited
                visited.add(theArtifact);
                ///add visited relationship
                addPath(visitedPath, parentCache.get(theArtifact), theArtifact);


                Set<Artifact> deps = theArtifact.getDeps();
                if (deps != null) {
                    for (Artifact artifact : deps) {
                        //update parent relationship
                        if (!parentCache.containsKey(artifact)) {
                            parentCache.put(artifact, theArtifact);
                        }

                        if (!visited.contains(artifact)) {
                            visitingQueue.add(artifactRepository.findOneByArtifactIdAndGroupId(artifact.getArtifactId(), artifact.getGroupId()));


                        }
                    }
                }
            }
        }

        System.out.println("########## full path ###############");
        visitedPath.forEach((x,y) -> {
            String path = String.join("-> ",y.stream().map(Artifact::toString).collect(Collectors.toList()));
            System.out.println("artifact: " + x.toString() + ", path:" + path);
        });

        System.out.println("########## end full path ###############");


        return visited;
    }




}
