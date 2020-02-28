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
public class ChildMergeStrategy implements MergeStrategy {

    @Autowired
    private ArtifactRepository artifactRepository;

    @Override
    public void merge() {
        merge(null);
    }

    //merge into child
    @Override
    public void merge(MergeFilter filter) {
        Collection<Artifact> artifactsToMergeToChild = artifactRepository.getAllArtifactByDeps(1, 0);

        if (filter != null) {
            if (filter.getGroupId() != null) {
                //do groupId filter
                artifactsToMergeToChild = artifactsToMergeToChild.stream().filter(x -> x.getGroupId().equals(filter.getGroupId())).collect(Collectors.toList());
            }

            if (filter.getArtifactId() != null) {
                //do groupId filter
                artifactsToMergeToChild = artifactsToMergeToChild.stream().filter(x -> x.getArtifactId().equals(filter.getArtifactId())).collect(Collectors.toList());
            }
        }

        for (Artifact artifact : artifactsToMergeToChild) {
            //get the child
            Collection<Artifact> children = artifactRepository.getChildren(artifact.getArtifactId(), artifact.getGroupId());
            if (CollectionUtils.isEmpty(children)) {
                log.warn("the target child node has been merged " + artifact.toString());
                break;
            }
            Artifact child = children.iterator().next();
            log.info("start merge node " + artifact.toString() + " to" + child.toString());
            doMerge(artifact, child);
            log.info("end merge node " + artifact.toString() + " to" + child.toString());
        }
    }

    public void doMerge(Artifact node, Artifact childNode) {
        //todo: to make sure parent & child have loaded deps
        if (CollectionUtils.isEmpty(node.getDeps())) {
            //populate deps
            node = artifactRepository.findOneByArtifactIdAndGroupId(node.getArtifactId(), node.getGroupId());
        }

        if (CollectionUtils.isEmpty(childNode.getDeps())) {
            //populate deps
            childNode = artifactRepository.findOneByArtifactIdAndGroupId(childNode.getArtifactId(), childNode.getGroupId());
        }

        //get all the parents of the node, in fact this strategy, there should no parent for the node
        Collection<Artifact> parents = artifactRepository.getAllParents(node.getArtifactId(), node.getGroupId());

        if (!CollectionUtils.isEmpty(parents)) {
            List<Artifact> parentsToPersist = new ArrayList<>();
            //link all the parents to childNode
            for (Artifact theOne : parents) {
                Artifact oneParent = artifactRepository.findOneByArtifactIdAndGroupId(theOne.getArtifactId(), theOne.getGroupId());
                oneParent.depends(childNode);

                parentsToPersist.add(oneParent);
            }
            artifactRepository.saveAll(parentsToPersist);
        }

        //add node to childnode mergepath


        //add parent path
        //add ancestors
        childNode.addPaths(node.makeMergePaths(PathType.CHILD));

        artifactRepository.save(childNode);

        artifactRepository.delete(node);
    }


}
