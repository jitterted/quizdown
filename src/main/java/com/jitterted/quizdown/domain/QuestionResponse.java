package com.jitterted.quizdown.domain;

public interface QuestionResponse {
    boolean isCorrect();

    Response response();

    int questionNumber();

    Question question();
}
