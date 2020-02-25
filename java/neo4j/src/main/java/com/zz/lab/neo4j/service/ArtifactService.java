package com.zz.lab.neo4j.service;


import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayDeque;
import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;

@Service
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

    public LinkedHashSet<Artifact> bfs(Artifact theNode) {
        Artifact startNode = artifactRepository.findOneByArtifactIdAndGroupId(theNode.getArtifactId(), theNode.getGroupId());

        LinkedHashSet<Artifact> visited = new LinkedHashSet<>();

        Queue<Artifact> visitingQueue = new ArrayDeque<>();
        visitingQueue.add(startNode);

        while (visitingQueue.size() != 0) {
            Artifact theArtifact = visitingQueue.poll();

            if (visited.contains(theArtifact)) {
                //visisted
                continue;
            } else {
                // not visited
                visited.add(theArtifact);
                Set<Artifact> deps = theArtifact.getDeps();
                if (deps != null) {
                    for (Artifact artifact : deps) {
                        if (!visited.contains(artifact)) {
                            visitingQueue.add(artifactRepository.findOneByArtifactIdAndGroupId(artifact.getArtifactId(), artifact.getGroupId()));
                        }

                    }

                }
            }
        }

        return visited;
    }




}
