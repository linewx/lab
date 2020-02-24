package com.zz.lab.neo4j.repo;

import com.zz.lab.neo4j.entity.Artifact;
import org.springframework.data.repository.CrudRepository;

public interface ArtifactRepository extends CrudRepository<Artifact, Long> {
    Artifact findByArtifactIdAndGroupId(String artifactId, String groupId);
}
