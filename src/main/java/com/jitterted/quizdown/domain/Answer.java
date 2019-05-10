package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
public class Answer {
  //  @NonNull
  private final Question question;
  @NonNull
  private final String response;

  public boolean isCorrect() {
    return question.isCorrectFor(response);
  }

  public String response() {
    return response;
  }
}
