package com.capturex.learningartifact.adapters.github;

import com.capturex.learningartifact.application.GitHubReadmeClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Component
public class GitHubApiReadmeClient implements GitHubReadmeClient {
    private static final Duration TIMEOUT = Duration.ofSeconds(3);
    private static final MediaType RAW_README_MEDIA_TYPE = MediaType.parseMediaType("application/vnd.github.raw+json");

    private final WebClient webClient;

    public GitHubApiReadmeClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("https://api.github.com")
                .build();
    }

    @Override
    public String fetchReadme(String owner, String repo) {
        return webClient.get()
                .uri("/repos/{owner}/{repo}/readme", owner, repo)
                .accept(RAW_README_MEDIA_TYPE)
                .retrieve()
                .bodyToMono(String.class)
                .block(TIMEOUT);
    }
}
