package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CreateLearningArtifactService Tests")
class CreateLearningArtifactServiceTest {

    @Mock
    private LearningArtifactRepository repository;

    @InjectMocks
    private CreateLearningArtifactService service;

    @Test
    @DisplayName("should_create_learning_artifact_when_data_is_valid")
    void should_create_learning_artifact_when_data_is_valid() {
        // Arrange
        String description = "Test artifact description";
        String insight = "Important lesson learned";
        String projectUrl = "https://example.com/artifact";
        Long generatedId = 123L;

        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                description,
                insight,
                projectUrl
        );

        LearningArtifact savedArtifact = new LearningArtifact(
                generatedId,
                description,
                insight,
                projectUrl
        );

        when(repository.save(any(LearningArtifact.class))).thenReturn(savedArtifact);

        // Act
        LearningArtifact result = service.create(request);

        // Assert
        // Verify repository.save was called once
        verify(repository, times(1)).save(any(LearningArtifact.class));

        // Verify fields are copied correctly
        assertNotNull(result, "Result should not be null");
        assertEquals(description, result.getDescription(), "Description should be copied correctly");
        assertEquals(insight, result.getInsight(), "Insight should be copied correctly");
        assertEquals(projectUrl, result.getProjectUrl(), "ProjectUrl should be copied correctly");

        // Verify id is not null
        assertNotNull(result.getId(), "Id should not be null");
        assertEquals(generatedId, result.getId(), "Id should match the returned id");
    }

    @Test
    @DisplayName("should_fail_when_descripcion_is_blank")
    void should_fail_when_descripcion_is_blank() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                "",
                "Important lesson learned",
                "https://example.com/artifact"
        );

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.create(request),
                "Should throw IllegalArgumentException when descripcion is blank");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }

    @Test
    @DisplayName("should_fail_when_request_is_null")
    void should_fail_when_request_is_null() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> service.create(null),
                "Should throw IllegalArgumentException when request is null");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }
}
