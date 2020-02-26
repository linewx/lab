package com.zz.lab.neo4j.service;

import com.zz.lab.neo4j.entity.Artifact;
import com.zz.lab.neo4j.entity.PathType;
import com.zz.lab.neo4j.repo.ArtifactRepository;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildMergeStrategy implements MergeStrategy {
    private Artifact node;
    private Artifact childNode;
    private ArtifactRepository artifactRepository;

    //merge into child
    @Override
    public void merge() {
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
