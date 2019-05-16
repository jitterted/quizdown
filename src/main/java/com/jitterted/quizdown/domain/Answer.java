package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@EqualsAndHashCode
public class Answer {
  @NonNull
  private final Question question;
  @NonNull
  private final Set<String> response;

  public Answer(Question question, String... response) {
    this.question = question;
    this.response = Set.of(response);
  }

  public boolean isCorrect() {
    return question.isCorrectFor(response);
  }

  @Override
  public String toString() {
    return "{Answer: Question = " + question + ", Response = " + response + "}";
  }

  public Set<String> response() {
    return response;
  }

  public int questionNumber() {
    return question.number();
  }

}
