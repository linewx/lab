package com.zz.lab.repo;

import com.zz.lab.entity.Artifact;
import com.zz.lab.entity.Person;
import org.springframework.data.repository.CrudRepository;

public interface ArtifactRepository extends CrudRepository<Artifact, Long> {

    Artifact findByArtifactIdAndGroupId(String artifactId, String groupId);
}
