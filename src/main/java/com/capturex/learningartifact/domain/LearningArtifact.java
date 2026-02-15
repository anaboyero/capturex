package com.capturex.learningartifact.domain;

import java.util.Objects;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class LearningArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String insight;
    private String projectUrl;

    public LearningArtifact(Long id, String description, String insight, String projectUrl) {
        this.id = id;
        this.description = description;
        this.insight = insight;
        this.projectUrl = projectUrl;
    }

    public LearningArtifact(String description, String insight, String projectUrl) {
        this.description = description;
        this.insight = insight;
        this.projectUrl = projectUrl;
    }

    public Long getId() {
        return id;
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
        LearningArtifact that = (LearningArtifact) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(insight, that.insight) &&
                Objects.equals(projectUrl, that.projectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, insight, projectUrl);
    }

    @Override
    public String toString() {
        return "LearningArtifact{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", insight='" + insight + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                '}';
    }
}
