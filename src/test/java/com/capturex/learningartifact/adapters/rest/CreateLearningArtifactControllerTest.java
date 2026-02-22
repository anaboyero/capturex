package com.capturex.learningartifact.adapters.rest;

import com.capturex.learningartifact.application.LearningArtifactUseCase;
import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@DisplayName("Controller Unit Tests")
class CreateLearningArtifactControllerTest {
    
    private LearningArtifactController controller;
    
    @Mock
    private LearningArtifactUseCase service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new LearningArtifactController(service);
    }
    
    @Test
    @DisplayName("should return 201 Created when artifact is created successfully")
    void shouldReturn201CreatedWhenArtifactIsCreated() {
        // Arrange
        String description = "Test description";
        String lessonLearned = "Test lessonLearned";
        String projectUrl = "https://example.com";
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            description, lessonLearned, projectUrl
        );
        
        LearningArtifact expectedArtifact = new LearningArtifact(description, lessonLearned, projectUrl);
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenReturn(expectedArtifact);
        
        // Act
        ResponseEntity<?> result = controller.create(request);
        
        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertInstanceOf(LearningArtifact.class, result.getBody());
        LearningArtifact body = (LearningArtifact) result.getBody();
        assertEquals(description, body.getDescription());
        assertEquals(lessonLearned, body.getLessonLearned());
        assertEquals(projectUrl, body.getProjectUrl());
        verify(service).create(request);
    }
    
    @Test
    @DisplayName("should return the created artifact in response body")
    void shouldReturnCreatedArtifactInResponseBody() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Learning async patterns", "Async/await simplifies code", "https://github.com/example"
        );
        
        LearningArtifact artifact = new LearningArtifact(
            "Learning async patterns", "Async/await simplifies code", "https://github.com/example"
        );
        when(service.create(request)).thenReturn(artifact);
        
        // Act
        ResponseEntity<?> result = controller.create(request);
        
        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertInstanceOf(LearningArtifact.class, result.getBody());
        LearningArtifact body = (LearningArtifact) result.getBody();
        assertEquals("Learning async patterns", body.getDescription());
        assertEquals("Async/await simplifies code", body.getLessonLearned());
        assertEquals("https://github.com/example", body.getProjectUrl());
    }
    
    @Test
    @DisplayName("should delegate to service when creating artifact")
    void shouldDelegateToServiceWhenCreating() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "LessonLearned", "https://test.com"
        );
        
        LearningArtifact artifact = new LearningArtifact("Test", "LessonLearned", "https://test.com");
        when(service.create(request)).thenReturn(artifact);
        
        // Act
        controller.create(request);
        
        // Assert
        verify(service).create(request);
    }
    
    @Test
    @DisplayName("should throw validation exception when service fails validation")
    void shouldThrowValidationException() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "", "LessonLearned", "https://test.com"
        );
        
        when(service.create(request))
            .thenThrow(new TooShortDescriptionException());
        
        // Act & Assert
        assertThrows(TooShortDescriptionException.class, () -> controller.create(request));
    }

    @Test
    @DisplayName("should throw null field exception when service detects null field")
    void shouldThrowNullFieldException() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            null, "LessonLearned", "https://test.com"
        );

        when(service.create(request))
            .thenThrow(new NullFieldException("description"));

        // Act & Assert
        assertThrows(NullFieldException.class, () -> controller.create(request));
    }
    
    @Test
    @DisplayName("should throw runtime exception when unexpected exception occurs")
    void shouldThrowRuntimeException() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "LessonLearned", "https://test.com"
        );
        
        when(service.create(request))
            .thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.create(request));
    }

    @Test
    @DisplayName("should return 200 OK and list when getting all artifacts")
    void shouldReturn200AndListWhenGetAll() {
        // Arrange
        LearningArtifact a1 = new LearningArtifact("d1", "i1", "u1");
        LearningArtifact a2 = new LearningArtifact("d2", "i2", "u2");
        List<LearningArtifact> list = Arrays.asList(a1, a2);
        when(service.getAll()).thenReturn(list);

        // Act
        ResponseEntity<List<LearningArtifact>> result = controller.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(2, result.getBody().size());
        assertEquals("d1", result.getBody().get(0).getDescription());
        verify(service).getAll();
    }

    @Test
    @DisplayName("should return 204 No Content when delete is successful")
    void shouldReturn204WhenDeleteSuccessful() {
        // Arrange
        Long id = 1L;
        // service.delete does nothing

        // Act
        ResponseEntity<Void> result = controller.delete(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(service).delete(id);
    }

    @Test
    @DisplayName("should throw exception when deleting non-existent artifact")
    void shouldThrowWhenDeleteNotFound() {
        // Arrange
        Long id = 2L;
        doThrow(new LearningArtifactNotFoundException(id)).when(service).delete(id);

        // Act & Assert
        assertThrows(LearningArtifactNotFoundException.class, () -> controller.delete(id));
        verify(service).delete(id);
    }
}
