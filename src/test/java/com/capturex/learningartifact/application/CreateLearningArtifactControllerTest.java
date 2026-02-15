package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
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
    
    private Controller controller;
    
    @Mock
    private CreateLearningArtifactServiceInterface service;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new Controller(service);
    }
    
    @Test
    @DisplayName("should return 201 Created when artifact is created successfully")
    void shouldReturn201CreatedWhenArtifactIsCreated() {
        // Arrange
        String description = "Test description";
        String insight = "Test insight";
        String projectUrl = "https://example.com";
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            description, insight, projectUrl
        );
        
        LearningArtifact expectedArtifact = new LearningArtifact(description, insight, projectUrl);
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenReturn(expectedArtifact);
        
        // Act
        ResponseEntity<LearningArtifact> result = controller.create(request);
        
        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(description, result.getBody().getDescription());
        assertEquals(insight, result.getBody().getInsight());
        assertEquals(projectUrl, result.getBody().getProjectUrl());
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
        ResponseEntity<LearningArtifact> result = controller.create(request);
        
        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Learning async patterns", result.getBody().getDescription());
        assertEquals("Async/await simplifies code", result.getBody().getInsight());
        assertEquals("https://github.com/example", result.getBody().getProjectUrl());
    }
    
    @Test
    @DisplayName("should delegate to service when creating artifact")
    void shouldDelegateToServiceWhenCreating() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "Insight", "https://test.com"
        );
        
        LearningArtifact artifact = new LearningArtifact("Test", "Insight", "https://test.com");
        when(service.create(request)).thenReturn(artifact);
        
        // Act
        controller.create(request);
        
        // Assert
        verify(service).create(request);
    }
    
    @Test
    @DisplayName("should return 400 Bad Request when service throws IllegalArgumentException")
    void shouldReturn400WhenIllegalArgumentException() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "", "Insight", "https://test.com"
        );
        
        when(service.create(request))
            .thenThrow(new IllegalArgumentException("Descripcion cannot be blank"));
        
        // Act
        ResponseEntity<LearningArtifact> result = controller.create(request);
        
        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
    
    @Test
    @DisplayName("should return 500 Internal Server Error when unexpected exception occurs")
    void shouldReturn500WhenRuntimeException() {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "Insight", "https://test.com"
        );
        
        when(service.create(request))
            .thenThrow(new RuntimeException("Database error"));
        
        // Act
        ResponseEntity<LearningArtifact> result = controller.create(request);
        
        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
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
    @DisplayName("should return 404 Not Found when deleting non-existent artifact")
    void shouldReturn404WhenDeleteNotFound() {
        // Arrange
        Long id = 2L;
        doThrow(new IllegalArgumentException("LearningArtifact not found")).when(service).delete(id);

        // Act
        ResponseEntity<Void> result = controller.delete(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(service).delete(id);
    }
}
