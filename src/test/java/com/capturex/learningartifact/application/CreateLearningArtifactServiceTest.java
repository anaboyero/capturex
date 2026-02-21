package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;
import com.capturex.learningartifact.domain.exceptions.EmptyLessonLearnedException;
import com.capturex.learningartifact.domain.exceptions.NotValidURLException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
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
        String description = "This is a valid artifact description for tests";
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
    @DisplayName("should_fail_when_description_is_too_short")
    void should_fail_when_description_is_too_short() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                "short description",
                "Important lesson learned",
                "https://example.com/artifact"
        );

        // Act & Assert
        assertThrows(TooShortDescriptionException.class, () -> service.create(request),
                "Should throw TooShortDescriptionException when description is too short");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }

    @Test
    @DisplayName("should_fail_when_lesson_learned_is_empty")
    void should_fail_when_lesson_learned_is_empty() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                "This is a valid artifact description for tests",
                "   ",
                "https://example.com/artifact"
        );

        // Act & Assert
        assertThrows(EmptyLessonLearnedException.class, () -> service.create(request),
                "Should throw EmptyLessonLearnedException when insight is empty");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }

    @Test
    @DisplayName("should_fail_when_project_url_is_not_valid")
    void should_fail_when_project_url_is_not_valid() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                "This is a valid artifact description for tests",
                "Important lesson learned",
                "invalid-url"
        );

        // Act & Assert
        assertThrows(NotValidURLException.class, () -> service.create(request),
                "Should throw NotValidURLException when URL format is invalid");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }

    @Test
    @DisplayName("should_fail_when_any_field_is_null")
    void should_fail_when_any_field_is_null() {
        // Arrange
        CreateLearningArtifactRequest nullDescription = new CreateLearningArtifactRequest(
                null,
                "Important lesson learned",
                "https://example.com/artifact"
        );
        CreateLearningArtifactRequest nullInsight = new CreateLearningArtifactRequest(
                "This is a valid artifact description for tests",
                null,
                "https://example.com/artifact"
        );
        CreateLearningArtifactRequest nullProjectUrl = new CreateLearningArtifactRequest(
                "This is a valid artifact description for tests",
                "Important lesson learned",
                null
        );

        // Act & Assert
        assertThrows(NullFieldException.class, () -> service.create(nullDescription),
                "Should throw NullFieldException when description is null");
        assertThrows(NullFieldException.class, () -> service.create(nullInsight),
                "Should throw NullFieldException when insight is null");
        assertThrows(NullFieldException.class, () -> service.create(nullProjectUrl),
                "Should throw NullFieldException when projectUrl is null");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }

    @Test
    @DisplayName("should_fail_when_request_is_null")
    void should_fail_when_request_is_null() {
        // Act & Assert
        assertThrows(NullFieldException.class, () -> service.create(null),
                "Should throw NullFieldException when request is null");

        // Verify repository.save was NOT called
        verify(repository, never()).save(any(LearningArtifact.class));
    }
}
