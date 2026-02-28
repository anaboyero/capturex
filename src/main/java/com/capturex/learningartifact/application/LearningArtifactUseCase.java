package com.capturex.learningartifact.application;

import com.capturex.learningartifact.adapters.rest.CreateLearningArtifactRequest;
import com.capturex.learningartifact.domain.LearningArtifact;
import java.util.List;

public interface LearningArtifactUseCase {
    LearningArtifact create(CreateLearningArtifactRequest request);
    ArtifactProposal suggest(String projectUrl);
    List<LearningArtifact> getAll();
    void delete(Long id);
}
