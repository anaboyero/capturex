package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    
    private final CreateLearningArtifactServiceInterface service;
    
    public Controller(CreateLearningArtifactServiceInterface service) {
        this.service = service;
    }
    
    @PostMapping("/learning-artifacts")
    public ResponseEntity<LearningArtifact> create(@RequestBody CreateLearningArtifactRequest request) {
        try {
            LearningArtifact artifact = service.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(artifact);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
