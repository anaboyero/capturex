package com.capturex.learningartifact.domain.exceptions;

public class CapturexException extends RuntimeException {

    public CapturexException(String message) {
        super(message);
    }

    public CapturexException(String message, Throwable cause) {
        super(message, cause);
    }
}
