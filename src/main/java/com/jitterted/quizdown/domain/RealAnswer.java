package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@EqualsAndHashCode
public class RealAnswer implements Answer {
  @NonNull
  private final Question question;
  @NonNull
  private final Set<String> response;

  public RealAnswer(Question question, String... response) {
    this.question = question;
    this.response = Set.of(response);
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
  public Set<String> response() {
    return response;
  }

  @Override
  public int questionNumber() {
    return question.number();
  }

}
