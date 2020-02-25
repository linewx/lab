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
        return this.groupId + ":" + this.artifactId + ":" + this.pathType.toString();
    }

    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
    PathType pathType;

    public static ArtifactPath fromString(String thePath) {
        String[] theArtifact = thePath.split(":");
        if (theArtifact.length != 3) {
            throw new RuntimeException("parse ArtifactPath error " + thePath);
        }else {
            return ArtifactPath.builder()
                    .groupId(theArtifact[0])
                    .artifactId(theArtifact[1])
                    .pathType(PathType.valueOf(theArtifact[2]))
                    .build();
        }
    }

}
