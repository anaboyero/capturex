package com.capturex.learningartifact.application;

import java.util.Objects;

public class CreateLearningArtifactRequest {
    private String descripcion;
    private String leccionAprendida;
    private String url;

    public CreateLearningArtifactRequest(String descripcion, String leccionAprendida, String url) {
        this.descripcion = descripcion;
        this.leccionAprendida = leccionAprendida;
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLeccionAprendida() {
        return leccionAprendida;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateLearningArtifactRequest that = (CreateLearningArtifactRequest) o;
        return Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(leccionAprendida, that.leccionAprendida) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(descripcion, leccionAprendida, url);
    }
}
