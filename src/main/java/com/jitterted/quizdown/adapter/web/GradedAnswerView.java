package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import lombok.Value;

import java.util.stream.Collectors;

@Value
public class GradedAnswerView {
  private final int questionNumber;
  private final String yourAnswer;
  private final boolean youAreCorrect;

  public static GradedAnswerView from(Answer answer) {
    return new GradedAnswerView(answer.questionNumber(),
                                responseAsString(answer),
                                answer.isCorrect());
  }

  private static String responseAsString(Answer answer) {
    return answer.response()
                 .stream()
                 .sorted()
                 .collect(Collectors.joining(", "));
  }
}
