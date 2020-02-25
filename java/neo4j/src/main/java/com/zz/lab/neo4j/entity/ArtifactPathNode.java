package com.zz.lab.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtifactPathNode {
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

    public static ArtifactPathNode fromString(String thePath) {
        String[] theArtifact = thePath.split(":");
        if (theArtifact.length != 3) {
            throw new RuntimeException("parse ArtifactPathNode error " + thePath);
        }else {
            return ArtifactPathNode.builder()
                    .groupId(theArtifact[0])
                    .artifactId(theArtifact[1])
                    .pathType(PathType.valueOf(theArtifact[2]))
                    .build();
        }
    }

}
