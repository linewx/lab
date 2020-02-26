package com.zz.lab.neo4j.repo;

import com.zz.lab.neo4j.entity.Artifact;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface ArtifactRepository extends Neo4jRepository<Artifact, Long> {
    Artifact findOneByArtifactIdAndGroupId(String artifactId, String groupId);

    Artifact findOneByArtifactIdAndGroupId(String artifactId, String groupId, int depth);

    List<Artifact> findAllByArtifactIdAndGroupId(String artifactId, String groupId);

    List<Artifact> findAllByGroupId(String groupId);

    List<Artifact> findAllByArtifactId(String artifactd);


    @Query("MATCH (a:Artifact) where size((a)-[:deps]->()) = {outDegree} or size((a)<-[:deps]-()) = {inDegree} RETURN a")
    Collection<Artifact> getAllArtifactByDeps(@Param("outDegree") Integer outDegree, @Param("inDegree") Integer inDegree);

   /* @Query("MATCH (a:Artifact {groupId: {group}}) where size((a)-[:deps]->()) = {outDegree} or size((a)<-[:deps]-()) = {inDegree} RETURN a")
    Collection<Artifact> getAllArtifactByDeps(@Param("outDegree") Integer outDegree, @Param("inDegree") Integer inDegree);
*/

    @Query("MATCH (parent)--> (a:Artifact {artifactId:{artifactId}, groupId: {groupId}}) return parent")
    Collection<Artifact> getAllParents(@Param("artifactId") String artifactId, @Param("groupId") String groupId);

    @Query("MATCH (a:Artifact {artifactId:{artifactId}, groupId:{groupId}})-->(child) return child")
    Collection<Artifact> getChildren(@Param("artifactId") String artifactId, @Param("groupId") String groupId);
}
