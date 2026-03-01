package com.capturex.learningartifact.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GitHubDescriptionProposalService Tests")
class GitHubDescriptionProposalServiceTest {

    @Mock
    private GitHubReadmeClient gitHubReadmeClient;

    private GitHubDescriptionProposalService service;

    @BeforeEach
    void setUp() {
        service = new GitHubDescriptionProposalService(gitHubReadmeClient, new ReadmeSummarizer());
    }

    @Test
    @DisplayName("should_return_write_a_description_for_non_github_url")
    void should_return_write_a_description_for_non_github_url() {
        ArtifactProposal proposal = service.suggest("https://example.com/repo");

        assertEquals("https://example.com/repo", proposal.getProjectUrl());
        assertEquals("Please, write a description for this project.", proposal.getDescription());
        verifyNoInteractions(gitHubReadmeClient);
    }

    @Test
    @DisplayName("should_return_generated_description_for_valid_github_url")
    void should_return_generated_description_for_valid_github_url() {
        String projectUrl = "https://github.com/octocat/hello-world";
        String readme = """
                # Hello World
                This repository shows a minimal GitHub project.
                It helps beginners understand the default workflow.
                """;

        when(gitHubReadmeClient.fetchReadme("octocat", "hello-world")).thenReturn(readme);

        ArtifactProposal proposal = service.suggest(projectUrl);

        assertEquals(projectUrl, proposal.getProjectUrl());
        assertTrue(proposal.getDescription().contains("This repository shows a minimal GitHub project."));
        verify(gitHubReadmeClient).fetchReadme("octocat", "hello-world");
    }

    @Test
    @DisplayName("should_fallback_to_empty_description_when_github_client_fails")
    void should_fallback_to_empty_description_when_github_client_fails() {
        String projectUrl = "https://github.com/octocat/hello-world";
        when(gitHubReadmeClient.fetchReadme("octocat", "hello-world"))
                .thenThrow(new RuntimeException("rate limit"));

        ArtifactProposal proposal = service.suggest(projectUrl);

        assertEquals(projectUrl, proposal.getProjectUrl());
        assertEquals("Please, write a description for this project.", proposal.getDescription());
    }

    @Test
    @DisplayName("should_truncate_generated_description_to_500_characters")
    void should_truncate_generated_description_to_500_characters() {
        String projectUrl = "https://github.com/octocat/hello-world";
        String readme = "a".repeat(700);
        when(gitHubReadmeClient.fetchReadme("octocat", "hello-world")).thenReturn(readme);

        ArtifactProposal proposal = service.suggest(projectUrl);

        assertEquals(500, proposal.getDescription().length());
    }
}
