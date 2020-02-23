package com.zz.lab.pom;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Dependency {
    String groupId;
    String artifactId;
    String version;
    String scope;
}
