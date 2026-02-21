package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.LearningArtifactRepository;
import com.capturex.learningartifact.domain.exceptions.EmptyLessonLearnedException;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import com.capturex.learningartifact.domain.exceptions.NotValidURLException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
public class CreateLearningArtifactService implements CreateLearningArtifactServiceInterface {
    private final LearningArtifactRepository repository;

    public CreateLearningArtifactService(LearningArtifactRepository repository) {
        this.repository = repository;
    }

    public LearningArtifact create(CreateLearningArtifactRequest request) {
        if (request == null) {
            throw new NullFieldException("request");
        }

        if (request.getDescription() == null) {
            throw new NullFieldException("description");
        }

        if (request.getLessonLearned() == null) {
            throw new NullFieldException("lessonLearned");
        }

        if (request.getProjectUrl() == null) {
            throw new NullFieldException("projectUrl");
        }

        if (request.getDescription().trim().length() < 30) {
            throw new TooShortDescriptionException();
        }

        if (request.getLessonLearned().isBlank()) {
            throw new EmptyLessonLearnedException();
        }

        if (!isValidUrl(request.getProjectUrl())) {
            throw new NotValidURLException();
        }

        LearningArtifact artifact = new LearningArtifact(
                request.getDescription(),
                request.getLessonLearned(),
                request.getProjectUrl()
        );
        return repository.save(artifact);
    }

    private boolean isValidUrl(String projectUrl) {
        if (projectUrl == null || projectUrl.isBlank()) {
            return false;
        }

        try {
            URI uri = new URI(projectUrl);
            String scheme = uri.getScheme();
            return uri.getHost() != null
                    && (scheme != null)
                    && ("http".equalsIgnoreCase(scheme) || "https".equalsIgnoreCase(scheme));
        } catch (URISyntaxException e) {
            return false;
        }
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
