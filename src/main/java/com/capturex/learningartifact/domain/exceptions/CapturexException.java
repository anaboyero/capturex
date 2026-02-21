package com.capturex.learningartifact.domain.exceptions;

public class CapturexException extends RuntimeException {
    private final ErrorCode errorCode;

    public CapturexException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public CapturexException(ErrorCode errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
