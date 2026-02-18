package com.capturex.learningartifact.domain;

public record Tag (String value){

    public Tag(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("La etiqueta no puede estar vacía");
        }
        String normalized = value.trim().toLowerCase().replaceAll("\\s+", "_");
        if (normalized.length() > 50) {
            throw new IllegalArgumentException("La etiqueta no puede tener más de 50 caracteres");
        }
        this.value = normalized;
    }
}