package com.zz.lab.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactPath {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public String toString() {
        return this.groupId + ":" + this.artifactId + ":" + this.pathType;
    }

    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
    String pathType;

}
