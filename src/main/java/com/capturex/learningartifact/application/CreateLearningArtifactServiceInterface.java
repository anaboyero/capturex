package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import java.util.List;

public interface CreateLearningArtifactServiceInterface {
    LearningArtifact create(CreateLearningArtifactRequest request);
    List<LearningArtifact> getAll();
    void delete(Long id);
}
