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
        
        if (request.getDescripcion() == null || request.getDescripcion().isBlank()) {
            throw new IllegalArgumentException("Descripcion cannot be blank");
        }
        
        LearningArtifact artifact = new LearningArtifact(
                request.getDescripcion(),
                request.getLeccionAprendida(),
                request.getUrl()
        );
        return repository.save(artifact);
    }
}
