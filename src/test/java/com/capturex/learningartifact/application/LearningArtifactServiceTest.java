package com.capturex.learningartifact.application;

import com.capturex.learningartifact.adapters.rest.CreateLearningArtifactRequest;
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
@DisplayName("LearningArtifactService Tests")
class LearningArtifactServiceTest {

    @Mock
    private LearningArtifactRepository repository;

    @Mock
    private DescriptionProposalService descriptionProposalService;

    @InjectMocks
    private LearningArtifactService service;

    @Test
    @DisplayName("should_create_learning_artifact_when_data_is_valid")
    void should_create_learning_artifact_when_data_is_valid() {
        // Arrange
        String description = "This is a valid artifact description for tests";
        String lessonLearned = "Important lesson learned";
        String projectUrl = "https://example.com/artifact";
        Long generatedId = 123L;

        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                description,
                lessonLearned,
                projectUrl
        );

        LearningArtifact savedArtifact = new LearningArtifact(
                generatedId,
                description,
                lessonLearned,
                projectUrl
        );

        when(repository.save(any(LearningArtifact.class))).thenReturn(savedArtifact);

        // Act
        LearningArtifact result = service.create(request);

        // Assert
        // Verify repository.save was called once
        verify(repository, times(1)).save(any(LearningArtifact.class));
        verifyNoInteractions(descriptionProposalService);

        // Verify fields are copied correctly
        assertNotNull(result, "Result should not be null");
        assertEquals(description, result.getDescription(), "Description should be copied correctly");
        assertEquals(lessonLearned, result.getLessonLearned(), "LessonLearned should be copied correctly");
        assertEquals(projectUrl, result.getProjectUrl(), "ProjectUrl should be copied correctly");

        // Verify id is not null
        assertNotNull(result.getId(), "Id should not be null");
        assertEquals(generatedId, result.getId(), "Id should match the returned id");
    }

    @Test
    @DisplayName("should_delegate_input_format_validation_to_controller")
    void should_delegate_input_format_validation_to_controller() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
                "short description",
                "Important lesson learned",
                "invalid-url"
        );
        LearningArtifact savedArtifact = new LearningArtifact(
                10L,
                request.getDescription(),
                request.getLessonLearned(),
                request.getProjectUrl()
        );

        when(repository.save(any(LearningArtifact.class))).thenReturn(savedArtifact);
        // Act & Assert
        LearningArtifact result = service.create(request);
        assertNotNull(result);
        verify(repository, times(1)).save(any(LearningArtifact.class));
        verifyNoInteractions(descriptionProposalService);
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
