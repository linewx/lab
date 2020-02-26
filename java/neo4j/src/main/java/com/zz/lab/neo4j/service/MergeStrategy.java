package com.zz.lab.neo4j.service;

public interface MergeStrategy {
    void merge();
    void merge(MergeFilter Filter);

}
