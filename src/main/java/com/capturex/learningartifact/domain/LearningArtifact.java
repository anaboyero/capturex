package com.capturex.learningartifact.domain;

import java.util.Objects;

public class LearningArtifact {
    private Long id;
    private String descripcion;
    private String leccionAprendida;
    private String url;

    public LearningArtifact(Long id, String descripcion, String leccionAprendida, String url) {
        this.id = id;
        this.descripcion = descripcion;
        this.leccionAprendida = leccionAprendida;
        this.url = url;
    }

    public LearningArtifact(String descripcion, String leccionAprendida, String url) {
        this.descripcion = descripcion;
        this.leccionAprendida = leccionAprendida;
        this.url = url;
    }

    public Long getId() {
        return id;
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
        LearningArtifact that = (LearningArtifact) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(descripcion, that.descripcion) &&
                Objects.equals(leccionAprendida, that.leccionAprendida) &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descripcion, leccionAprendida, url);
    }

    @Override
    public String toString() {
        return "LearningArtifact{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", leccionAprendida='" + leccionAprendida + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
