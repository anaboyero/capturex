package com.capturex.learningartifact.application;

public class ErrorResponse {
    private final ErrorResponseCode code;
    private final String message;

    public ErrorResponse(ErrorResponseCode code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorResponseCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
