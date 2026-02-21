package com.capturex.learningartifact.domain.exceptions;

public class NullFieldException extends CapturexException {

    public NullFieldException(String fieldName) {
        super(ErrorCode.NULL_FIELD, fieldName + " cannot be null");
    }
}
