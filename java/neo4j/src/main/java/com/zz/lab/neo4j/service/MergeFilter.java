package com.zz.lab.neo4j.service;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MergeFilter {
    private String artifactId;
    private String groupId;
}
