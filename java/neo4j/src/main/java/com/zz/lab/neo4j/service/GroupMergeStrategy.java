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
import java.util.Map;
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
        List<Map<String, Object>> results = artifactRepository.findGroupByGroupId();

        //do filter
        if (filter != null) {
            results = results.stream().filter(x -> filter.getGroupId().equals(x.get("groupId"))).collect(Collectors.toList());
        }

        if (!CollectionUtils.isEmpty(results)) {
            for (Map<String, Object> one : results) {
                String groupId = (String)one.get("groupId");
                Long count = (Long)one.get("count");

                Collection<Artifact> groupedArtifacts = artifactRepository.findAllByGroupId(groupId);

                //create new group node
                Artifact groupNode = Artifact.builder()
                        .groupId(groupId)
                        .artifactId("group")
                        .build();


                if (!CollectionUtils.isEmpty(groupedArtifacts)) {
                    artifactRepository.save(groupNode);
                    groupedArtifacts.forEach(x -> doMerge(x, groupNode));
                }
            }
        }

    }

    public void doMerge(Artifact node, Artifact groupNode) {
        log.info("start merge node: " + node.getArtifactId() + " and " + groupNode.getArtifactId());
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
