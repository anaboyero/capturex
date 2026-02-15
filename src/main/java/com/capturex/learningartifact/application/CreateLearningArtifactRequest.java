package com.capturex.learningartifact.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;

public class CreateLearningArtifactRequest {
    private String description;
    private String insight;
    private String projectUrl;

    @JsonCreator
    public CreateLearningArtifactRequest(
            @JsonProperty("description") String description,
            @JsonProperty("insight") String insight,
            @JsonProperty("projectUrl") String projectUrl) {
        this.description = description;
        this.insight = insight;
        this.projectUrl = projectUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getInsight() {
        return insight;
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateLearningArtifactRequest that = (CreateLearningArtifactRequest) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(insight, that.insight) &&
                Objects.equals(projectUrl, that.projectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, insight, projectUrl);
    }
}
