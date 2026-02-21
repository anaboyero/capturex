package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.exceptions.CapturexException;
import com.capturex.learningartifact.domain.exceptions.ErrorCode;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CapturexException.class)
    public ResponseEntity<ErrorResponse> handleCapturexException(CapturexException exception) {
        ErrorResponse response = new ErrorResponse(mapDomainErrorCode(exception.getErrorCode()), exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(LearningArtifactNotFoundException.class)
    public ResponseEntity<Void> handleLearningArtifactNotFoundException(LearningArtifactNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        ErrorResponse response = new ErrorResponse(ErrorResponseCode.INVALID_REQUEST, exception.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getRejectedValue() == null
                        ? error.getField() + " cannot be null"
                        : error.getDefaultMessage())
                .orElse("Request validation failed");

        ErrorResponse response = new ErrorResponse(ErrorResponseCode.INVALID_REQUEST, message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Void> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    private ErrorResponseCode mapDomainErrorCode(ErrorCode domainCode) {
        return switch (domainCode) {
            case TOO_SHORT_DESCRIPTION -> ErrorResponseCode.TOO_SHORT_DESCRIPTION;
            case EMPTY_LESSON_LEARNED -> ErrorResponseCode.EMPTY_LESSON_LEARNED;
            case NOT_VALID_URL -> ErrorResponseCode.NOT_VALID_URL;
            case NULL_FIELD -> ErrorResponseCode.NULL_FIELD;
            case VALIDATION_ERROR -> ErrorResponseCode.VALIDATION_ERROR;
        };
    }
}
