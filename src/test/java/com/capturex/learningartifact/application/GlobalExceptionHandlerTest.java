package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("GlobalExceptionHandler Tests")
class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    @DisplayName("should map CapturexException to 400 with code and message")
    void shouldMapCapturexExceptionToBadRequest() {
        ResponseEntity<ErrorResponse> response = handler.handleCapturexException(new TooShortDescriptionException());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorResponseCode.TOO_SHORT_DESCRIPTION, response.getBody().getCode());
    }

    @Test
    @DisplayName("should map NullFieldException to 400 with null field code")
    void shouldMapNullFieldExceptionToBadRequest() {
        ResponseEntity<ErrorResponse> response = handler.handleCapturexException(new NullFieldException("description"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorResponseCode.NULL_FIELD, response.getBody().getCode());
        assertEquals("description cannot be null", response.getBody().getMessage());
    }

    @Test
    @DisplayName("should map LearningArtifactNotFoundException to 404")
    void shouldMapLearningArtifactNotFoundExceptionToNotFound() {
        ResponseEntity<Void> response =
                handler.handleLearningArtifactNotFoundException(new LearningArtifactNotFoundException(2L));

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("should map IllegalArgumentException to 400 with invalid request code")
    void shouldMapIllegalArgumentExceptionToBadRequest() {
        ResponseEntity<ErrorResponse> response =
                handler.handleIllegalArgumentException(new IllegalArgumentException("Invalid input"));

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ErrorResponseCode.INVALID_REQUEST, response.getBody().getCode());
    }

    @Test
    @DisplayName("should map RuntimeException to 500")
    void shouldMapRuntimeExceptionToInternalServerError() {
        ResponseEntity<Void> response = handler.handleRuntimeException(new RuntimeException("boom"));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
