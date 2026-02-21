package com.capturex.learningartifact.application;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class CreateLearningArtifactRequest {
    @NotBlank(message = "description must not be blank")
    @Size(min = 30, max = 500, message = "description must be between 30 and 500 characters")
    private String description;

    @NotBlank(message = "insight must not be blank")
    @Size(min = 10, message = "insight must be at least 10 characters")
    private String insight;

    @NotBlank(message = "projectUrl must not be blank")
    @Pattern(
            regexp = "^(https?://).+",
            message = "projectUrl must be a valid URL starting with http:// or https://"
    )
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
