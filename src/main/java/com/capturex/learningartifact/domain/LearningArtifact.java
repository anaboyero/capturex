package com.capturex.learningartifact.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class LearningArtifact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1500)
    private String description;

    private String lessonLearned;
    private String projectUrl;

    protected LearningArtifact() {
        // JPA requires a no-arg constructor
    }

    public LearningArtifact(Long id, String description, String lessonLearned, String projectUrl) {
        this.id = id;
        this.description = description;
        this.lessonLearned = lessonLearned;
        this.projectUrl = projectUrl;
    }

    public LearningArtifact(String description, String lessonLearned, String projectUrl) {
        this.description = description;
        this.lessonLearned = lessonLearned;
        this.projectUrl = projectUrl;
    }

    public Long getId() {
        return id;
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
        LearningArtifact that = (LearningArtifact) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(description, that.description) &&
                Objects.equals(lessonLearned, that.lessonLearned) &&
                Objects.equals(projectUrl, that.projectUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, lessonLearned, projectUrl);
    }

    @Override
    public String toString() {
        return "LearningArtifact{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", lessonLearned='" + lessonLearned + '\'' +
                ", projectUrl='" + projectUrl + '\'' +
                '}';
    }
}
