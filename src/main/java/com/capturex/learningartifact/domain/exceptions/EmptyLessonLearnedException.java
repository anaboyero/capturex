package com.capturex.learningartifact.domain.exceptions;

public class EmptyLessonLearnedException extends CapturexException {

    public EmptyLessonLearnedException() {
        super(ErrorCode.EMPTY_LESSON_LEARNED, "Lesson learned cannot be empty");
    }
}
