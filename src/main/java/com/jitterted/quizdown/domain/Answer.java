package com.jitterted.quizdown.domain;

public interface Answer {
    boolean isCorrect();

    Response response();

    int questionNumber();

    Question question();
}
