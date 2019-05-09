package com.jitterted.quizdown;

import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode
public class AnswerValidator {
  private final List<String> correctChoices;

  public AnswerValidator(String... answers) {
    correctChoices = List.of(answers);
  }

  public boolean isCorrectFor(String response) {
    return correctChoices.contains(response.strip().toLowerCase());
  }

  @Override
  public String toString() {
    return "AnswerValidator: " + correctChoices;
  }
}
