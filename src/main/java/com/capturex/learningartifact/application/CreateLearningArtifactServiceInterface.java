package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;

public interface CreateLearningArtifactServiceInterface {
    LearningArtifact create(CreateLearningArtifactRequest request);
}
