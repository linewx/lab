package com.zz.lab.neo4j.service;


import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArtifactService {
    @Autowired
    private ArtifactRepository artifactRepository;

    @Transactional
    public void addRelation(Artifact foo, Artifact bar) {
        Artifact theFoo = artifactRepository.findByArtifactIdAndGroupId(foo.getArtifactId(), foo.getGroupId());
        if (theFoo == null) {
            theFoo = artifactRepository.save(foo);
        }

        Artifact theBar = artifactRepository.findByArtifactIdAndGroupId(bar.getArtifactId(), bar.getGroupId());
        if (theBar == null) {
            theBar = artifactRepository.save(bar);
        }

        theFoo.depends(theBar);
        artifactRepository.save(theFoo);
    }

    public void deleteAll() {
        artifactRepository.deleteAll();
    }




}
