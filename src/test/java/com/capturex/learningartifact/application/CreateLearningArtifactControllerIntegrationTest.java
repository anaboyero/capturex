package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = Controller.class)
@DisplayName("CreateLearningArtifactControllerIntegration")
class CreateLearningArtifactControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateLearningArtifactServiceInterface service;

    @Test
    @DisplayName("should return 201 Created when creating a learning artifact")
    void shouldReturn201CreatedWhenCreatingArtifact() throws Exception {
        // Arrange
        String description = "Test description";
        String insight = "Test insight";
        String projectUrl = "https://example.com";
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            description, insight, projectUrl
        );

        LearningArtifact artifact = new LearningArtifact(description, insight, projectUrl);
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenReturn(artifact);

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description", equalTo(description)))
            .andExpect(jsonPath("$.insight", equalTo(insight)))
            .andExpect(jsonPath("$.projectUrl", equalTo(projectUrl)));
    }

    @Test
    @DisplayName("should return artifact data in response body")
    void shouldReturnArtifactDataInResponseBody() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Learning patterns", "Design patterns improve code", "https://github.com/example"
        );

        LearningArtifact artifact = new LearningArtifact(
            "Learning patterns", "Design patterns improve code", "https://github.com/example"
        );
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenReturn(artifact);

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.description", equalTo("Learning patterns")))
            .andExpect(jsonPath("$.insight", equalTo("Design patterns improve code")))
            .andExpect(jsonPath("$.projectUrl", equalTo("https://github.com/example")));
    }

    @Test
    @DisplayName("should return 400 Bad Request when description is blank")
    void shouldReturn400WhenDescriptionIsBlank() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "", "Insight", "https://example.com"
        );

        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenThrow(new IllegalArgumentException("Descripcion cannot be blank"));

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should return 400 Bad Request when request is null")
    void shouldReturn400WhenRequestIsNull() throws Exception {
        // Arrange
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenThrow(new IllegalArgumentException("Request cannot be null"));

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content("{}")
        )
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("should accept POST request at /learning-artifacts endpoint")
    void shouldAcceptPostRequestAtLearningArtifactsEndpoint() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "Insight", "https://test.com"
        );

        LearningArtifact artifact = new LearningArtifact("Test", "Insight", "https://test.com");
        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenReturn(artifact);

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("should return 500 when unexpected error occurs")
    void shouldReturn500WhenUnexpectedErrorOccurs() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Test", "Insight", "https://test.com"
        );

        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isInternalServerError());
    }
}
