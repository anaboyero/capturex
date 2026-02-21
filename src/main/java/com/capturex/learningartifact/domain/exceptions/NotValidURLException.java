package com.capturex.learningartifact.domain.exceptions;

public class NotValidURLException extends CapturexException {

    public NotValidURLException() {
        super(ErrorCode.NOT_VALID_URL, "Project URL is not valid");
    }
}
