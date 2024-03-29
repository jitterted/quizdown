package com.jitterted.quizdown.domain;

public class StubQuestionResponse implements QuestionResponse {

    private final boolean correct;
    private final Response response;
    private final int questionNumber;

    public StubQuestionResponse(boolean correct, Response response, int questionNumber) {
        this.correct = correct;
        this.response = response;
        this.questionNumber = questionNumber;
    }

    @Override
    public boolean isCorrect() {
        return correct;
    }

    @Override
    public Response response() {
        return response;
    }

    @Override
    public int questionNumber() {
        return questionNumber;
    }

    @Override
    public Question question() {
        return null;
    }
}
