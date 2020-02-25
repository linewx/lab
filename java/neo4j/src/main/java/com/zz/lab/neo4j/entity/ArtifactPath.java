package com.zz.lab.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactPath implements Cloneable{
    private List<ArtifactPathNode> path = new ArrayList<>();

    @Override
    public String toString() {
        return String.join("->", path.stream().map(ArtifactPathNode::toString).collect(Collectors.toList()));
    }


    public static ArtifactPath fromString(String thePath) {
        List<String> parsedPath = Arrays.asList(thePath.split("->"));
        List<ArtifactPathNode> path = parsedPath.stream().map(ArtifactPathNode::fromString).collect(Collectors.toList());
        return ArtifactPath.builder().path(path).build();
    }

    public void addNode(ArtifactPathNode node) {
         path.add(node);
    }

    @Override
    public ArtifactPath clone() {
        return ArtifactPath.builder()
                .path(new ArrayList<>(path))
                .build();
    }



}
