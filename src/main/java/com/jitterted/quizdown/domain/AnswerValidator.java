package com.jitterted.quizdown.domain;

public interface AnswerValidator {
  boolean isCorrectFor(Response response);
}
