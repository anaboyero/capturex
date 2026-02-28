package com.capturex.learningartifact.adapters.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Objects;

public class CreateLearningArtifactRequest {
    @NotBlank(message = "description must not be blank")
    @Size(max = 500, message = "description must be at most 500 characters")
    private String description;

    @NotBlank(message = "lessonLearned must not be blank")
    @Size(min = 10, message = "lessonLearned must be at least 10 characters")
    private String lessonLearned;

    @NotBlank(message = "projectUrl must not be blank")
    @Pattern(
            regexp = "^(https?://).+",
            message = "projectUrl must be a valid URL starting with http:// or https://"
    )
    private String projectUrl;

    @JsonCreator
    public CreateLearningArtifactRequest(
            @JsonProperty("description") String description,
            @JsonProperty("lessonLearned") String lessonLearned,
            @JsonProperty("projectUrl") String projectUrl) {
        this.description = normalize(description);
        this.lessonLearned = normalize(lessonLearned);
        this.projectUrl = normalize(projectUrl);
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }

    public String getDescription() {
        return description;
    }

    public String getLessonLearned() {
        return lessonLearned;
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
                Objects.equals(lessonLearned, that.lessonLearned) &&
                Objects.equals(projectUrl, that.projectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, lessonLearned, projectUrl);
    }
}
