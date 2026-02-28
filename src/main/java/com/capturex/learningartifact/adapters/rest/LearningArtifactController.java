package com.capturex.learningartifact.adapters.rest;

import com.capturex.learningartifact.application.LearningArtifactUseCase;
import com.capturex.learningartifact.application.ArtifactProposal;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class LearningArtifactController {
    
    private final LearningArtifactUseCase service;
    
    public LearningArtifactController(LearningArtifactUseCase service) {
        this.service = service;
    }
    
    @PostMapping("/learning-artifacts")
    public ResponseEntity<LearningArtifact> create(@Valid @RequestBody CreateLearningArtifactRequest request) {
        LearningArtifact artifact = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(artifact);
    }

    @PostMapping("/learning-artifacts/proposal")
    public ResponseEntity<ArtifactProposal> suggest(@Valid @RequestBody ArtifactProposalRequest request) {
        ArtifactProposal proposal = service.suggest(request.getProjectUrl());
        return ResponseEntity.ok(proposal);
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
