package com.jitterted.quizdown.domain;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Answer {
  @NonNull
  private final Question question;
  @NonNull
  private final String response;

  public boolean isCorrect() {
    return question.isCorrectFor(response);
  }
}
