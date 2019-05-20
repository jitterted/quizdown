package com.jitterted.quizdown.domain;

import java.util.Set;

public interface Answer {
  boolean isCorrect();

  Set<String> response();

  int questionNumber();
}
