package com.capturex.learningartifact.persistence;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:file:./target/test-h2-persistence;MODE=MySQL",
    "spring.jpa.hibernate.ddl-auto=update"
})
@DisplayName("H2 Persistence Tests")
class H2PersistenceTest {

    @Autowired
    private LearningArtifactRepository repository;

    @Test
    @DisplayName("should persist LearningArtifact to H2 file database")
    void shouldPersistLearningArtifactToH2File() {
        // Arrange
        LearningArtifact artifact = new LearningArtifact(
            "Learn persistence",
            "H2 file database stores data permanently",
            "https://example.com/h2-guide"
        );

        // Act
        LearningArtifact saved = repository.save(artifact);

        // Assert
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals("Learn persistence", saved.getDescription());
        assertEquals("H2 file database stores data permanently", saved.getLessonLearned());
        assertEquals("https://example.com/h2-guide", saved.getProjectUrl());

        // Verify it can be retrieved
        LearningArtifact retrieved = repository.findById(saved.getId()).orElse(null);
        assertNotNull(retrieved);
        assertEquals(saved.getDescription(), retrieved.getDescription());
    }

    @Test
    @DisplayName("should retrieve multiple persisted artifacts from H2 file")
    void shouldRetrieveMultipleArtifactsFromH2File() {
        // Arrange
        LearningArtifact a1 = new LearningArtifact("a1", "i1", "u1");
        LearningArtifact a2 = new LearningArtifact("a2", "i2", "u2");
        repository.save(a1);
        repository.save(a2);

        // Act
        var all = repository.findAll();

        // Assert
        assertTrue(all.size() >= 2, "Should have at least 2 artifacts");
    }

    @Test
    @DisplayName("should delete artifact from H2 file and verify deletion")
    void shouldDeleteArtifactFromH2File() {
        // Arrange
        LearningArtifact artifact = new LearningArtifact("to delete", "will be deleted", "https://delete.com");
        LearningArtifact saved = repository.save(artifact);
        Long id = saved.getId();

        // Act
        repository.deleteById(id);

        // Assert
        assertFalse(repository.existsById(id), "Artifact should be deleted from H2");
    }
}
