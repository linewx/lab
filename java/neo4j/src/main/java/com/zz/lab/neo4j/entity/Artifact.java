package com.zz.lab.neo4j.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import java.util.*;

@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artifact {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public String toString() {
        return this.groupId + ":" + this.artifactId;
    }


    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
    String source;

    @Convert(ArtifactMergePathConverter.class)
    List<ArtifactPath> mergePaths = new ArrayList<>();

    @Relationship(type = "deps")
    Set<Artifact> deps;

    public void depends(Artifact artifact) {

        if (deps == null) {
            deps = new HashSet<>();
        }

        deps.add(artifact);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Artifact artifact = (Artifact) o;
        return groupId.equals(artifact.groupId) &&
                artifactId.equals(artifact.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId);
    }



}
