package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.exceptions.CapturexException;
import com.capturex.learningartifact.domain.exceptions.EmptyLessonLearnedException;
import com.capturex.learningartifact.domain.exceptions.NotValidURLException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    
    private final CreateLearningArtifactServiceInterface service;
    
    public Controller(CreateLearningArtifactServiceInterface service) {
        this.service = service;
    }
    
    @PostMapping("/learning-artifacts")
    public ResponseEntity<?> create(@RequestBody CreateLearningArtifactRequest request) {
        try {
            LearningArtifact artifact = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(artifact);
        } catch (CapturexException e) {
            return ResponseEntity.badRequest().body(buildValidationError(e));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("INVALID_REQUEST", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private ErrorResponse buildValidationError(CapturexException exception) {
        if (exception instanceof TooShortDescriptionException) {
            return new ErrorResponse("TOO_SHORT_DESCRIPTION", exception.getMessage());
        }
        if (exception instanceof EmptyLessonLearnedException) {
            return new ErrorResponse("EMPTY_LESSON_LEARNED", exception.getMessage());
        }
        if (exception instanceof NotValidURLException) {
            return new ErrorResponse("NOT_VALID_URL", exception.getMessage());
        }
        if (exception instanceof NullFieldException) {
            return new ErrorResponse("NULL_FIELD", exception.getMessage());
        }
        return new ErrorResponse("VALIDATION_ERROR", exception.getMessage());
    }

    @GetMapping("/learning-artifacts")
    public ResponseEntity<List<LearningArtifact>> getAll() {
        try {
            List<LearningArtifact> artifacts = service.getAll();
            return ResponseEntity.ok(artifacts);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/learning-artifacts/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
