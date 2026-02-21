package com.capturex.learningartifact.application;

import com.capturex.learningartifact.domain.LearningArtifact;
import com.capturex.learningartifact.domain.exceptions.LearningArtifactNotFoundException;
import com.capturex.learningartifact.domain.exceptions.NullFieldException;
import com.capturex.learningartifact.domain.exceptions.TooShortDescriptionException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = LearningArtifactController.class)
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
        String description = "Basic controller that registers a learning artifact from a developer";
        String lessonLearned = "How to work with an agent throught TDD to create a very simple vertical slice";
        String projectUrl = "https://example.com";
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            description, lessonLearned, projectUrl
        );

        LearningArtifact artifact = new LearningArtifact(description, lessonLearned, projectUrl);
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
            .andExpect(jsonPath("$.lessonLearned", equalTo(lessonLearned)))
            .andExpect(jsonPath("$.projectUrl", equalTo(projectUrl)));
    }

    @Test
    @DisplayName("should return artifact data in response body")
    void shouldReturnArtifactDataInResponseBody() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Basic controller that registers a learning artifact from a developer", "How to work with an agent throught TDD to create a very simple vertical slice", "https://github.com/example"
        );

        LearningArtifact artifact = new LearningArtifact(
            "Basic controller that registers a learning artifact from a developer", "How to work with an agent throught TDD to create a very simple vertical slice", "https://github.com/example"
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
            .andExpect(jsonPath("$.description", equalTo("Basic controller that registers a learning artifact from a developer")))
            .andExpect(jsonPath("$.lessonLearned", equalTo("How to work with an agent throught TDD to create a very simple vertical slice")))
            .andExpect(jsonPath("$.projectUrl", equalTo("https://github.com/example")));
    }

    @Test
    @DisplayName("should return 400 Bad Request automatically when request violates bean validation")
    void shouldReturn400AutomaticallyWhenRequestViolatesBeanValidation() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "", "LessonLearned", "https://example.com"
        );

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", equalTo("INVALID_REQUEST")))
            .andExpect(jsonPath("$.message").isString());

        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("should return 400 Bad Request automatically when request has null fields")
    void shouldReturn400AutomaticallyWhenRequestHasNullFields() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            null, "LessonLearned", "https://example.com"
        );

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", equalTo("INVALID_REQUEST")))
            .andExpect(jsonPath("$.message").isString());

        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("should return 400 Bad Request when request body misses required fields")
    void shouldReturn400WhenRequestIsNull() throws Exception {
        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content("{}")
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", equalTo("INVALID_REQUEST")));

        verifyNoInteractions(service);
    }

    @Test
    @DisplayName("should return 400 with domain validation error when service throws CapturexException")
    void shouldReturn400WhenServiceThrowsDomainValidationException() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "This is a valid artifact description for tests",
            "Important lesson learned",
            "https://example.com/artifact"
        );

        when(service.create(any(CreateLearningArtifactRequest.class)))
            .thenThrow(new TooShortDescriptionException());

        // Act & Assert
        mockMvc.perform(
            post("/learning-artifacts")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.code", equalTo("TOO_SHORT_DESCRIPTION")))
            .andExpect(jsonPath("$.message", equalTo("Description must be at least 30 characters long")));
    }

    @Test
    @DisplayName("should accept POST request at /learning-artifacts endpoint")
    void shouldAcceptPostRequestAtLearningArtifactsEndpoint() throws Exception {
        // Arrange
        CreateLearningArtifactRequest request = new CreateLearningArtifactRequest(
            "Basic controller that registers a learning artifact from a developer", "How to work with an agent throught TDD to create a very simple vertical slice", "https://test.com"
        );

        LearningArtifact artifact = new LearningArtifact("Test", "LessonLearned", "https://test.com");
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
            "Basic controller that registers a learning artifact from a developer", "How to work with an agent throught TDD to create a very simple vertical slice", "https://test.com"
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

    @Test
    @DisplayName("should return 200 OK and list when getting all artifacts (integration)")
    void shouldReturn200AndListWhenGetAllIntegration() throws Exception {
        // Arrange
        LearningArtifact a1 = new LearningArtifact("d1", "i1", "u1");
        LearningArtifact a2 = new LearningArtifact("d2", "i2", "u2");
        List<LearningArtifact> list = Arrays.asList(a1, a2);
        when(service.getAll()).thenReturn(list);

        // Act & Assert
        mockMvc.perform(
            get("/learning-artifacts")
        )
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].description", equalTo("d1")))
            .andExpect(jsonPath("$[1].description", equalTo("d2")));
    }

    @Test
    @DisplayName("should return 500 when unexpected error occurs on GET")
    void shouldReturn500WhenGetAllThrows() throws Exception {
        // Arrange
        when(service.getAll()).thenThrow(new RuntimeException("Database error on getAll"));

        // Act & Assert
        mockMvc.perform(
            get("/learning-artifacts")
        )
            .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("should return 204 No Content when delete is successful (integration)")
    void shouldReturn204WhenDeleteSuccessfulIntegration() throws Exception {
        // Arrange
        Long id = 1L;
        // service.delete does nothing for success

        // Act & Assert
        mockMvc.perform(
            delete("/learning-artifacts/{id}", id)
        )
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("should return 404 Not Found when deleting non-existent artifact (integration)")
    void shouldReturn404WhenDeleteNotFoundIntegration() throws Exception {
        // Arrange
        Long id = 2L;
        doThrow(new LearningArtifactNotFoundException(id)).when(service).delete(id);

        // Act & Assert
        mockMvc.perform(
            delete("/learning-artifacts/{id}", id)
        )
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("should return 500 Internal Server Error when delete throws unexpected error (integration)")
    void shouldReturn500WhenDeleteThrowsIntegration() throws Exception {
        // Arrange
        Long id = 3L;
        doThrow(new RuntimeException("DB error on delete")).when(service).delete(id);

        // Act & Assert
        mockMvc.perform(
            delete("/learning-artifacts/{id}", id)
        )
            .andExpect(status().isInternalServerError());
    }
}
