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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
