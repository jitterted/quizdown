package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import lombok.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value
public class GradedAnswerView {
  public static final int ELIDED_STRING_SIZE = 60;
  private final int questionNumber;
  private final String questionText;
  private final String yourAnswer;
  private final boolean youAreCorrect;

  public static GradedAnswerView from(Answer answer) {
    return new GradedAnswerView(answer.questionNumber(),
                                elidedQuestionText(answer),
                                responseAsString(answer),
                                answer.isCorrect());
  }

  private static String elidedQuestionText(Answer answer) {
    String content = answer.question().content();
    if (content.length() < ELIDED_STRING_SIZE) {
      return content;
    }
    return content.substring(0, ELIDED_STRING_SIZE) + "...";
  }

  public static List<GradedAnswerView> toResultsView(Set<Answer> answerSet) {
    return answerSet.stream()
                    .map(GradedAnswerView::from)
                    .sorted(Comparator.comparingInt(GradedAnswerView::getQuestionNumber))
                    .collect(Collectors.toList());
  }

  private static String responseAsString(Answer answer) {
    return answer.response().asSet()
                 .stream()
                 .sorted()
                 .collect(Collectors.joining(", "));
  }
}
