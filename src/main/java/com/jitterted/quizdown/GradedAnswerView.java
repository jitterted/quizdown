package com.jitterted.quizdown;

import lombok.Data;

@Data
public class GradedAnswerView {
  private final int questionNumber;
  private final String yourAnswer;
  private final String correctAnswer;
  private final boolean youAreCorrect;
}
