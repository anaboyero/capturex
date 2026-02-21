package com.capturex.learningartifact.domain.exceptions;

public class EmptyLessonLearnedException extends CapturexException {

    public EmptyLessonLearnedException() {
        super("Lesson learned cannot be empty");
    }
}
