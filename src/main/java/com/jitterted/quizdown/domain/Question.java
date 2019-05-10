package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Question {
  @NonNull
  private final QuestionType type;
  @NonNull
  private final String content;
  //  @NonNull
  private final AnswerValidator answer;

  public boolean isCorrectFor(String response) {
    return answer.isCorrectFor(response);
  }

  @Override
  public String toString() {
    return type.toString() + ": " + content + " -> " + answer;
  }

  public QuestionType type() {
    return type;
  }

  public String content() {
    return content;
  }
}
