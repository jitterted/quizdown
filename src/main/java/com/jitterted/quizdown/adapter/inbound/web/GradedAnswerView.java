package com.jitterted.quizdown.adapter.inbound.web;

import com.jitterted.quizdown.domain.QuestionResponse;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record GradedAnswerView(int questionNumber, String questionText, String yourAnswer, boolean youAreCorrect) {
    public static final int ELIDED_STRING_SIZE = 60;

    public static GradedAnswerView from(QuestionResponse questionResponse) {
        return new GradedAnswerView(questionResponse.questionNumber(),
                                    elidedQuestionText(questionResponse),
                                    responseAsString(questionResponse),
                                    questionResponse.isCorrect());
    }

    private static String elidedQuestionText(QuestionResponse questionResponse) {
        String content = questionResponse.question().content();
        if (content.length() < ELIDED_STRING_SIZE) {
            return content;
        }
        return content.substring(0, ELIDED_STRING_SIZE) + "...";
    }

    public static List<GradedAnswerView> toResultsView(Set<QuestionResponse> questionResponseSet) {
        return questionResponseSet.stream()
                                  .map(GradedAnswerView::from)
                                  .sorted(Comparator.comparingInt(GradedAnswerView::questionNumber))
                                  .collect(Collectors.toList());
    }

    private static String responseAsString(QuestionResponse questionResponse) {
        return questionResponse.response().asSet()
                               .stream()
                               .sorted()
                               .collect(Collectors.joining(", "));
    }
}
