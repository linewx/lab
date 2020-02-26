package com.zz.lab.neo4j.service;

import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.entity.PathType;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ParentMergeStrategy implements MergeStrategy {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Override
    public void merge() {
        merge(null);
    }

    //merge into parent
    @Override
    public void merge(MergeFilter filter) {
        Collection<Artifact> artifactsToMergeToParent = artifactRepository.getAllArtifactByDeps(0, 1);

        //do filter
        if (filter != null) {
            if (filter.getGroupId() != null) {
                //do groupId filter
                artifactsToMergeToParent = artifactsToMergeToParent.stream().filter(x -> x.getGroupId().equals(filter.getGroupId())).collect(Collectors.toList());
            }

            if (filter.getArtifactId() != null) {
                //do groupId filter
                artifactsToMergeToParent = artifactsToMergeToParent.stream().filter(x -> x.getArtifactId().equals(filter.getArtifactId())).collect(Collectors.toList());
            }
        }

        for (Artifact artifact : artifactsToMergeToParent) {
            //get the child
            Collection<Artifact> children = artifactRepository.getAllParents(artifact.getArtifactId(), artifact.getGroupId());
            Artifact child = children.iterator().next();
            log.info("start merge node " + artifact.toString() + " to" + child.toString());
            doMerge(artifact, child);
            log.info("end merge node " + artifact.toString() + " to" + child.toString());
        }
    }

    public void doMerge(Artifact node, Artifact parentNode) {
        //todo: to make sure parent & child have loaded deps
        if (CollectionUtils.isEmpty(node.getDeps())) {
            //populate deps
            node = artifactRepository.findOneByArtifactIdAndGroupId(node.getArtifactId(), node.getGroupId());
        }

        if (CollectionUtils.isEmpty(parentNode.getDeps())) {
            //populate deps
            parentNode = artifactRepository.findOneByArtifactIdAndGroupId(parentNode.getArtifactId(), parentNode.getGroupId());
        }

        //get all the children of the node, in fact this strategy, there should no parent for the node

        if (!CollectionUtils.isEmpty(node.getDeps())) {
            List<Artifact> parentsToPersist = new ArrayList<>();
            //link all the parents to childNode
            for (Artifact oneChild : node.getDeps()) {
                //todo: should no need to populate child
                //Artifact oneChild = artifactRepository.findOneByArtifactIdAndGroupId(theOne.getArtifactId(), theOne.getGroupId());
                parentNode.depends(oneChild);
            }

        }

        //add node to childnode mergepath


        //add parent path
        //add ancestors
        parentNode.addPaths(node.makeMergePaths(PathType.PARENT));

        artifactRepository.save(parentNode);

        artifactRepository.delete(node);
    }


}
