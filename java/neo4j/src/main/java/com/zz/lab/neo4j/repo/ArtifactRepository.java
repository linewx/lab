package com.zz.lab.neo4j.repo;

import com.zz.lab.neo4j.entity.Artifact;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface ArtifactRepository extends Neo4jRepository<Artifact, Long> {
    Artifact findByArtifactIdAndGroupId(String artifactId, String groupId);


    @Query("MATCH (a:Artifact) where size((a)-[:deps]->()) = {outDegree} or size((a)<-[:deps]-()) = {inDegree} RETURN a")
    Collection<Artifact> getAllArtifactByDeps(@Param("outDegree") Integer outDegree, @Param("inDegree") Integer inDegree) ;

    @Query("MATCH (parent)--> (a:Artifact {artifactId:{artifactId}}) return parent")
    Collection<Artifact> getAllParents(@Param("artifactId") String artifactId);

    @Query("MATCH (a:Artifact {artifactId:{artifactId}})-->(child) return child")
    Collection<Artifact> getChildren(@Param("artifactId") String artifactId);
}
