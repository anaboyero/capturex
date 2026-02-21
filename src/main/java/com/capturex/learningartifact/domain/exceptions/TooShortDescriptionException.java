package com.capturex.learningartifact.domain.exceptions;

public class TooShortDescriptionException extends CapturexException {

    public TooShortDescriptionException() {
        super(ErrorCode.TOO_SHORT_DESCRIPTION, "Description must be at least 30 characters long");
    }
}
