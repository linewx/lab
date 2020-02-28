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
public class GroupMergeStrategy implements MergeStrategy {

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
            Collection<Artifact> parents = artifactRepository.getAllParents(artifact.getArtifactId(), artifact.getGroupId());
            if (CollectionUtils.isEmpty(parents)) {
                log.warn("the target parent node has been merged:" + artifact.toString());
                break;
            }
            Artifact parent = parents.iterator().next();
            log.info("start merge node " + artifact.toString() + " to" + parent.toString());
            doMerge(artifact, parent);
            log.info("end merge node " + artifact.toString() + " to" + parent.toString());
        }
    }

    public void doMerge(Artifact node, Artifact groupNode) {
        //todo: to make sure parent & child have loaded deps
        if (CollectionUtils.isEmpty(node.getDeps())) {
            //populate deps
            node = artifactRepository.findOneByArtifactIdAndGroupId(node.getArtifactId(), node.getGroupId());
        }

        //make the group node up to date
        groupNode = artifactRepository.findOneByArtifactIdAndGroupId(groupNode.getArtifactId(), groupNode.getGroupId());

        //build relationship between all the parent of the node and the groupNode
        //1. find all the parents
        Collection<Artifact> parentNodes = artifactRepository.getAllParents(node.getArtifactId(), node.getGroupId());
        //List<Artifact> parents = new ArrayList<>();
        if (!CollectionUtils.isEmpty(parentNodes)) {
            for (Artifact artifact: parentNodes) {
                //populate parent node
                artifact = artifactRepository.findOneByArtifactIdAndGroupId(artifact.getArtifactId(), artifact.getGroupId());
                artifact.depends(groupNode);
            }
        }
        artifactRepository.saveAll(parentNodes);

        //build relationship between all the child of the node and the groupNode
        Collection<Artifact> childNodes = artifactRepository.getChildren(node.getArtifactId(), node.getGroupId());
        if (!CollectionUtils.isEmpty(childNodes)) {
            childNodes.forEach(groupNode::depends);
        }


        //update merge path
        groupNode.addPaths(node.makeMergePaths(PathType.GROUP));


        //add parent path
        //add ancestors

        artifactRepository.save(groupNode);

        artifactRepository.delete(node);
    }


}
