package com.jitterted.quizdown.domain;

public class DummyAnswerValidator implements AnswerValidator {
    @Override
    public boolean isCorrectFor(Response response) {
        throw new UnsupportedOperationException("This is a dummy.");
    }

}
