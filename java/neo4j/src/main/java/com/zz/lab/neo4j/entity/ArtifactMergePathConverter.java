package com.zz.lab.neo4j.entity;

import org.neo4j.ogm.typeconversion.AttributeConverter;

import java.util.List;

public class ArtifactMergePathConverter implements AttributeConverter<List<ArtifactPath>, String> {
    @Override
    public String toGraphProperty(List<ArtifactPath> strings) {
        return null;
    }

    @Override
    public List<ArtifactPath> toEntityAttribute(String s) {
        return null;
    }
}
