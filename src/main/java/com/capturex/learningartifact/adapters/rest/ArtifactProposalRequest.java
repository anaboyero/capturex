package com.capturex.learningartifact.adapters.rest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ArtifactProposalRequest {
    @NotBlank(message = "projectUrl must not be blank")
    @Pattern(
            regexp = "^(https?://).+",
            message = "projectUrl must be a valid URL starting with http:// or https://"
    )
    private final String projectUrl;

    @JsonCreator
    public ArtifactProposalRequest(@JsonProperty("projectUrl") String projectUrl) {
        this.projectUrl = normalize(projectUrl);
    }

    public String getProjectUrl() {
        return projectUrl;
    }

    private String normalize(String value) {
        return value == null ? null : value.trim();
    }
}
