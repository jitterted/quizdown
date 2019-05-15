package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Question {
  @NonNull
  private final QuestionType type;
  @NonNull
  private final String content;
  @NonNull
  private final AnswerValidator answerValidator;
  private final int number;

  public Question(QuestionType questionType, String content, AnswerValidator validator) {
    this(questionType, content, validator, -1);
  }

  public boolean isCorrectFor(@NonNull Set<String> response) {
    return answerValidator.isCorrectFor(response);
  }

  @Override
  public String toString() {
    return "{Question: #" + number +
        ", Type: " + type.toString() +
        ", Content: " + content +
        ", Answer Validator = " + answerValidator + "}";
  }

  public QuestionType type() {
    return type;
  }

  public String content() {
    return content;
  }

  public int number() {
    return number;
  }
}
