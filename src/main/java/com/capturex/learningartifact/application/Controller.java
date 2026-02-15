package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
