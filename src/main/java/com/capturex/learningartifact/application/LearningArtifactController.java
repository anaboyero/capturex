package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import jakarta.validation.Valid;
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
public class LearningArtifactController {
    
    private final CreateLearningArtifactServiceInterface service;
    
    public LearningArtifactController(CreateLearningArtifactServiceInterface service) {
        this.service = service;
    }
    
    @PostMapping("/learning-artifacts")
    public ResponseEntity<LearningArtifact> create(@Valid @RequestBody CreateLearningArtifactRequest request) {
        LearningArtifact artifact = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(artifact);
    }

    @GetMapping("/learning-artifacts")
    public ResponseEntity<List<LearningArtifact>> getAll() {
        List<LearningArtifact> artifacts = service.getAll();
        return ResponseEntity.ok(artifacts);
    }

    @DeleteMapping("/learning-artifacts/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
