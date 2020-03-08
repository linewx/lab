package com.zz.lab.neo4j.repo;

import com.zz.lab.neo4j.entity.RawArtifact;
import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface RawArtifactRepository extends Neo4jRepository<RawArtifact, Long> {
    RawArtifact findOneByArtifactIdAndGroupId(String artifactId, String groupId);

    RawArtifact findOneByArtifactId(String artifactId, @Depth int depth);

    RawArtifact findOneByArtifactId(String artifactId);

    RawArtifact findOneByArtifactIdAndGroupId(String artifactId, String groupId, @Depth int depth);

    List<RawArtifact> findAllByArtifactIdAndGroupId(String artifactId, String groupId);

    List<RawArtifact> findAllByGroupId(String groupId);

    List<RawArtifact> findAllByArtifactId(String artifactd);


    @Query("MATCH (a:RawArtifact) where size((a)-[:deps]->()) = {outDegree} and size((a)<-[:deps]-()) = {inDegree} RETURN a")
    Collection<RawArtifact> getAllArtifactByDeps(@Param("outDegree") Integer outDegree, @Param("inDegree") Integer inDegree);

   /* @Query("MATCH (a:RawArtifact {groupId: {group}}) where size((a)-[:deps]->()) = {outDegree} or size((a)<-[:deps]-()) = {inDegree} RETURN a")
    Collection<Artifact> getAllArtifactByDeps(@Param("outDegree") Integer outDegree, @Param("inDegree") Integer inDegree);
*/

    @Query("MATCH (parent)--> (a:RawArtifact {artifactId:{artifactId}, groupId: {groupId}}) return parent")
    Collection<RawArtifact> getAllParents(@Param("artifactId") String artifactId, @Param("groupId") String groupId);

    @Query("MATCH (a:RawArtifact {artifactId:{artifactId}, groupId:{groupId}})-->(child) return child")
    Collection<RawArtifact> getChildren(@Param("artifactId") String artifactId, @Param("groupId") String groupId);

    @Query("match (n:RawArtifact) return n.groupId as groupId, count(n.groupId) as number")
    List<Map<String, Object>> findGroupByGroupId();

    @Query("MATCH (a:RawArtifact)  RETURN a as artifact, size((a)-[:deps]->()) as outdegree, size((a)<-[:deps]-()) as indegree")
    List<Map<String, Object>> findAllDeps();

}
