package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class DefaultAnswerValidator implements AnswerValidator {
  private final QuestionType questionType;
  private final Response correctResponse;

  public DefaultAnswerValidator(QuestionType questionType, String... correctResponse) {
    this.questionType = questionType;
    this.correctResponse = Response.of(correctResponse);
  }

  public static AnswerValidatorBuilder forType(QuestionType questionType) {
    return new AnswerValidatorBuilder(questionType);
  }

  @Override
  public boolean isCorrectFor(Response response) {
    switch (questionType) {
      case FIB:
        return response.matchesAny(correctResponse);
      case MC:
        return response.allMatch(correctResponse);
      default:
        throw new IllegalStateException("Type " + questionType + " was not expected.");
    }
  }

  @Override
  public String toString() {
    return "{AnswerValidator: " + correctResponse + "}";
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
