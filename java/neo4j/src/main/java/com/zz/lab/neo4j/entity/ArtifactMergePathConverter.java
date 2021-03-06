package com.zz.lab.neo4j.entity;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ArtifactMergePathConverter implements AttributeConverter<List<ArtifactPath>, String> {
    @Override
    public String toGraphProperty(List<ArtifactPath> mergePaths) {
        if (mergePaths == null || mergePaths.isEmpty()) {
            return "";
        }
        return String.join(",", mergePaths.stream().map(ArtifactPath::toString).collect(Collectors.toList()));


    }

    @Override
    public List<ArtifactPath> toEntityAttribute(String s) {
        if (s == null || s.isEmpty()) {
            return new ArrayList<>();
        }
        List<String> paths = Arrays.asList(s.split(","));
        return paths.stream().map(ArtifactPath::fromString).collect(Collectors.toList());
    }
}
