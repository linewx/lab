package com.zz.lab.neo4j.service;


import com.zz.lab.neo4j.entity.RawArtifact;
import com.zz.lab.neo4j.repo.RawArtifactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RawArtifactService {
    @Autowired
    private RawArtifactRepository artifactRepository;

    @Transactional
    public void addRelation(RawArtifact foo, RawArtifact bar) {
        RawArtifact theFoo = artifactRepository.findOneByArtifactIdAndGroupId(foo.getArtifactId(), foo.getGroupId());
        if (theFoo == null) {
            theFoo = artifactRepository.save(foo);
        }

        RawArtifact theBar = artifactRepository.findOneByArtifactIdAndGroupId(bar.getArtifactId(), bar.getGroupId());
        if (theBar == null) {
            theBar = artifactRepository.save(bar);
        }

        theFoo.depends(theBar);
        artifactRepository.save(theFoo);
    }

    public void deleteAll() {
        artifactRepository.deleteAll();
    }

    private void addPath(HashMap<RawArtifact,  List<RawArtifact>> visitedPath, RawArtifact parent, RawArtifact node) {
        if (parent == null) {
            visitedPath.put(node, new ArrayList<>());
        }else {
            if (!visitedPath.containsKey(parent)) {
                log.error("error to insert node" + node.toString());
                throw new RuntimeException("parent node : " + parent.toString() + " should be visited");
            }
            List<RawArtifact> newPath = new ArrayList<>(visitedPath.get(parent));
            newPath.add(parent);
            visitedPath.put(node, newPath);
        }

    }
    public LinkedHashSet<RawArtifact> bfs(RawArtifact theNode) {
        RawArtifact startNode = artifactRepository.findOneByArtifactIdAndGroupId(theNode.getArtifactId(), theNode.getGroupId());

        LinkedHashSet<RawArtifact> visited = new LinkedHashSet<>();
        LinkedHashMap<RawArtifact, List<RawArtifact>> visitedPath = new LinkedHashMap<>();
        Map<RawArtifact, RawArtifact> parentCache = new HashMap<>();


        Queue<RawArtifact> visitingQueue = new ArrayDeque<>();
        visitingQueue.add(startNode);
        addPath(visitedPath, null, startNode);


        while (visitingQueue.size() != 0) {
            RawArtifact theArtifact = visitingQueue.poll();

            if (visited.contains(theArtifact)) {
                //visisted
                continue;
            } else {
                // not visited
                visited.add(theArtifact);
                ///add visited relationship
                addPath(visitedPath, parentCache.get(theArtifact), theArtifact);


                Set<RawArtifact> deps = theArtifact.getDeps();
                if (deps != null) {
                    for (RawArtifact artifact : deps) {
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
            String path = String.join("-> ",y.stream().map(RawArtifact::toString).collect(Collectors.toList()));
            System.out.println("artifact: " + x.toString() + ", path:" + path);
        });

        System.out.println("########## end full path ###############");


        return visited;
    }




}
