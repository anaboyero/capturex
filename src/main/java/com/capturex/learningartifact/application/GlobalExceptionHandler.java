package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.exceptions.CapturexException;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapturexException.class)
    public ResponseEntity<ErrorResponse> handleCapturexException(CapturexException exception) {
        ErrorResponse response = new ErrorResponse(exception.getErrorCode().name(), exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(LearningArtifactNotFoundException.class)
    public ResponseEntity<Void> handleLearningArtifactNotFoundException(LearningArtifactNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponse response = new ErrorResponse("INVALID_REQUEST", exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
