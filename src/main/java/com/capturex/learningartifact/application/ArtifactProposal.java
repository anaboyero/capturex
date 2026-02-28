package com.capturex.learningartifact.application;

import java.util.Objects;

public class ArtifactProposal {
    private final String projectUrl;
    private final String description;

    public ArtifactProposal(String projectUrl, String description) {
        this.projectUrl = projectUrl;
        this.description = description;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArtifactProposal that = (ArtifactProposal) o;
        return Objects.equals(projectUrl, that.projectUrl) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectUrl, description);
    }
}
