package com.zz.lab.entity;

import lombok.Data;
import lombok.ToString;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Set;

@Data
@ToString
@NodeEntity
public class Artifact {
    @Id
    @GeneratedValue
    private Long id;
    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;

    @Relationship(type = "deps")
    Set<Artifact> deps;

}
