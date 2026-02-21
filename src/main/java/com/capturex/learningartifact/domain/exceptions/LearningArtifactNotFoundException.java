package com.capturex.learningartifact.domain.exceptions;

public class LearningArtifactNotFoundException extends RuntimeException {

    public LearningArtifactNotFoundException(Long id) {
        super("LearningArtifact not found: " + id);
    }
}
