package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;

public class CreateLearningArtifactService {
    private final LearningArtifactRepository repository;

    public CreateLearningArtifactService(LearningArtifactRepository repository) {
        this.repository = repository;
    }

    public LearningArtifact create(CreateLearningArtifactRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        
        if (request.getDescription() == null || request.getDescription().isBlank()) {
            throw new IllegalArgumentException("Descripcion cannot be blank");
        }
        
        LearningArtifact artifact = new LearningArtifact(
                request.getDescription(),
                request.getInsight(),
                request.getProjectUrl()
        );
        return repository.save(artifact);
    }
}
