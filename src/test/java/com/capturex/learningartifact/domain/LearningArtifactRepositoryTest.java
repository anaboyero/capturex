package com.capturex.learningartifact.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("LearningArtifactRepository Integration Tests")
class LearningArtifactRepositoryTest {

    @Autowired
    private LearningArtifactRepository repository;

    @Test
    @DisplayName("should_persist_correct_learning_artifact")
    void should_persist_learning_artifact() {
        // Arrange
        String description = "Integration test artifact";
        String lessonLearned = "Learned about JPA persistence";
        String projectUrl = "https://example.com/test";

        LearningArtifact artifact = new LearningArtifact(
                description,
                lessonLearned,
                projectUrl
        );

        // Act
        LearningArtifact savedArtifact = repository.save(artifact);

        // Assert - Verify the artifact was saved with an id
        assertNotNull(savedArtifact.getId(), "Saved artifact should have an id");

        // Act - Retrieve the artifact by id
        Optional<LearningArtifact> retrievedArtifact = repository.findById(savedArtifact.getId());

        // Assert - Verify the artifact exists and fields match
        assertTrue(retrievedArtifact.isPresent(), "Artifact should be found by id");

        LearningArtifact foundArtifact = retrievedArtifact.get();
        assertEquals(description, foundArtifact.getDescription(), "Description should match");
        assertEquals(lessonLearned, foundArtifact.getLessonLearned(), "LessonLearned should match");
        assertEquals(projectUrl, foundArtifact.getProjectUrl(), "ProjectUrl should match");
        assertEquals(savedArtifact.getId(), foundArtifact.getId(), "Id should match");
    }
}
