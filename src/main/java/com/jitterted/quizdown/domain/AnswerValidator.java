package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
public class AnswerValidator {
  private final Set<String> correctChoices;

  public AnswerValidator(String... answers) {
    correctChoices = Set.of(answers);
  }

  public boolean isCorrectFor(String response) {
    return correctChoices.contains(response.strip().toLowerCase());
  }

  @Override
  public String toString() {
    return "{AnswerValidator: " + correctChoices + "}";
  }
}
