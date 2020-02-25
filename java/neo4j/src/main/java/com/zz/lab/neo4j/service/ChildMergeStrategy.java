package com.zz.lab.neo4j.service;

import com.zz.lab.neo4j.entity.Artifact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
public class ChildMergeStrategy implements MergeStrategy{
    private Artifact node;
    private Artifact childNode;

    @Override
    public void merge() {

    }


}
