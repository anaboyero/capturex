package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateLearningArtifactService implements CreateLearningArtifactServiceInterface {
    private final LearningArtifactRepository repository;

    public CreateLearningArtifactService(LearningArtifactRepository repository) {
        this.repository = repository;
    }

    public LearningArtifact create(CreateLearningArtifactRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        LearningArtifact artifact = new LearningArtifact(
                request.getDescription(),
                request.getLessonLearned(),
                request.getProjectUrl()
        );
        return repository.save(artifact);
    }

    public List<LearningArtifact> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (!repository.existsById(id)) {
            throw new LearningArtifactNotFoundException(id);
        }
        repository.deleteById(id);
    }
}
