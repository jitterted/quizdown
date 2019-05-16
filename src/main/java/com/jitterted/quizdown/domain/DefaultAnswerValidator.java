package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@EqualsAndHashCode
public class DefaultAnswerValidator implements AnswerValidator {
  private final QuestionType questionType;
  private final Set<String> correctChoices;

  public DefaultAnswerValidator(QuestionType questionType, String... correctChoices) {
    this.questionType = questionType;
    this.correctChoices = Set.of(correctChoices);
  }

  public static AnswerValidatorBuilder forType(QuestionType questionType) {
    return new AnswerValidatorBuilder(questionType);
  }

  @Override
  public boolean isCorrectFor(@NonNull Set<String> response) {
    return switch (questionType) {
      case FIB -> correctChoices.containsAll(response);
      case MC -> correctChoices.equals(response);
    };
  }

  @Override
  public String toString() {
    return "{AnswerValidator: " + correctChoices + "}";
  }

  public static class AnswerValidatorBuilder {
    private final QuestionType questionType;

    public AnswerValidatorBuilder(QuestionType questionType) {
      this.questionType = questionType;
    }

    public DefaultAnswerValidator correctChoices(String... correctChoices) {
      return new DefaultAnswerValidator(questionType, correctChoices);
    }
  }
}
