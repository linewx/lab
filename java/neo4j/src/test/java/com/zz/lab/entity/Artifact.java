package com.zz.lab.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Artifact {
    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
}
