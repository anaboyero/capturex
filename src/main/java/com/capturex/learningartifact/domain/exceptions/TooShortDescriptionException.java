package com.capturex.learningartifact.domain.exceptions;

public class TooShortDescriptionException extends CapturexException {

    public TooShortDescriptionException() {
        super("Description must be at least 30 characters long");
    }
}
