package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
public class RealAnswer implements Answer {
  @NonNull
  private final Question question;
  private final Response response;

  public RealAnswer(Question question, String... response) {
    this.question = question;
    this.response = Response.of(response);
  }

  @Override
  public boolean isCorrect() {
    return question.isCorrectFor(response);
  }

  @Override
  public String toString() {
    return "{Answer: Question = " + question + ", Response = " + response + "}";
  }

  @Override
  public Response response() {
    return response;
  }

  @Override
  public int questionNumber() {
    return question.number();
  }

}
