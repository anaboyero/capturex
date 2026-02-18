package com.capturex.learningartifact.domain;

public record AskRequest(
    String question,
    String context) {}
