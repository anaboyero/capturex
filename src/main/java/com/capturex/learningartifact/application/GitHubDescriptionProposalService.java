package com.capturex.learningartifact.application;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class GitHubDescriptionProposalService implements DescriptionProposalService {
    private static final Pattern GITHUB_REPO_URL_PATTERN =
            Pattern.compile("^https://github\\.com/([^/]+)/([^/]+)$");
    private static final int MAX_DESCRIPTION_CHARS = 500;

    private final GitHubReadmeClient gitHubReadmeClient;
    private final ReadmeSummarizer readmeSummarizer;

    public GitHubDescriptionProposalService(GitHubReadmeClient gitHubReadmeClient, ReadmeSummarizer readmeSummarizer) {
        this.gitHubReadmeClient = gitHubReadmeClient;
        this.readmeSummarizer = readmeSummarizer;
    }

    @Override
    public ArtifactProposal suggest(String projectUrl) {
        String normalizedProjectUrl = normalize(projectUrl);
        if (normalizedProjectUrl == null) {
            return new ArtifactProposal("", "");
        }

        Matcher matcher = GITHUB_REPO_URL_PATTERN.matcher(normalizedProjectUrl);
        if (!matcher.matches()) {
            return new ArtifactProposal(normalizedProjectUrl, "");
        }

        String owner = matcher.group(1);
        String repo = matcher.group(2);

        try {
            String readme = gitHubReadmeClient.fetchReadme(owner, repo);
            String description = readmeSummarizer.summarize(readme, MAX_DESCRIPTION_CHARS);
            return new ArtifactProposal(normalizedProjectUrl, description);
        } catch (RuntimeException exception) {
            return new ArtifactProposal(normalizedProjectUrl, "");
        }
    }

    private String normalize(String value) {
        if (value == null) {
            return null;
        }
        return value.trim();
    }
}
