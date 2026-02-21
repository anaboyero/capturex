package com.capturex.learningartifact.domain.exceptions;

public class NullFieldException extends CapturexException {

    public NullFieldException(String fieldName) {
        super(fieldName + " cannot be null");
    }
}
