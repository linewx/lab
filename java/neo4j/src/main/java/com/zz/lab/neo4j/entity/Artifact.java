package com.zz.lab.neo4j.entity;

import lombok.*;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@Data
@ToString
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Artifact {
    @Id
    @GeneratedValue
    private Long id;
    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
    String source;

    @Relationship(type = "deps")
    Set<Artifact> deps;

    public void depends(Artifact artifact) {

        if (deps == null) {
            deps = new HashSet<>();
        }

        deps.add(artifact);
    }
}
