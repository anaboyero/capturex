package com.capturex.learningartifact.domain.exceptions;

public class NotValidURLException extends CapturexException {

    public NotValidURLException() {
        super("Project URL is not valid");
    }
}
