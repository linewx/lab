package com.zz.lab.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Data
@NodeEntity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawArtifact {
    @Id
    @GeneratedValue
    private Long id;

    @Override
    public String toString() {
        return this.groupId + ":" + this.artifactId;
    }


    String groupId;
    String artifactId;
    String version;
    String scope;
    String packing;
    String source;

    @Convert(ArtifactMergePathConverter.class)
    List<ArtifactPath> mergePaths = new ArrayList<>();

    @Relationship(type = "deps")
    Set<RawArtifact> deps;

    public void depends(RawArtifact artifact) {

        if (deps == null) {
            deps = new HashSet<>();
        }

        deps.add(artifact);
    }

    public void addPath(ArtifactPath artifactPath) {
        if (mergePaths == null) {
            mergePaths = new ArrayList<>();
        }

        mergePaths.add(artifactPath);
    }

    public void addPaths(Collection<ArtifactPath> artifactPaths) {
        if (mergePaths == null) {
            mergePaths = new ArrayList<>();
        }

        mergePaths.addAll(artifactPaths);
    }

    public List<ArtifactPath> makeMergePaths(PathType pathType) {
        List<ArtifactPath> paths = new ArrayList<>();
        ArtifactPath selfPath = this.makeSelfPath(pathType);

        ArtifactPathNode artifactPathNode = this.makeSelfNode(pathType);

        // add own path
        paths.add(selfPath);

        // merge own to merge path
        if (!CollectionUtils.isEmpty(mergePaths)) {
            mergePaths.forEach(x -> {
                ArtifactPath y = x.clone();
                y.addNode(artifactPathNode);
                paths.add(y);
            });
        }
        return paths;

    }

    private ArtifactPath makeSelfPath(PathType pathType) {
        return ArtifactPath.builder()
                .path(Arrays.asList(makeSelfNode(pathType)))
                .build();
    }

    private ArtifactPathNode makeSelfNode(PathType pathType) {
        return ArtifactPathNode.builder()
                .pathType(pathType)
                .artifactId(this.artifactId)
                .groupId(this.getGroupId())
                .build();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RawArtifact artifact = (RawArtifact) o;
        return groupId.equals(artifact.groupId) &&
                artifactId.equals(artifact.artifactId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, artifactId);
    }



}
