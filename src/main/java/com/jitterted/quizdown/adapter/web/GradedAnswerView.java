package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
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

  public static List<GradedAnswerView> toResultsView(Set<Answer> answerSet) {
    return answerSet.stream()
                    .map(GradedAnswerView::from)
                    .sorted(Comparator.comparingInt(GradedAnswerView::getQuestionNumber))
                    .collect(Collectors.toList());
  }

  private static String responseAsString(Answer answer) {
    return answer.response()
                 .stream()
                 .sorted()
                 .collect(Collectors.joining(", "));
  }
}
