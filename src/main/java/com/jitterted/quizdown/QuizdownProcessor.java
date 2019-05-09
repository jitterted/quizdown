package com.jitterted.quizdown;

import java.util.Scanner;

public class QuizdownProcessor {

  private final HtmlTransformer fillInTheBlank;
  private final HtmlTransformer multipleChoiceTransformer;

  public QuizdownProcessor() {
    fillInTheBlank = new FillInTheBlankTransformer();
    multipleChoiceTransformer = new MultipleChoiceTransformer();
  }

  public String toHtml(String quizdown) {
    HtmlTransformer htmlTransformer = transformerFor(quizdown);
    return "<form method='post' action='/answer'>\n" +
        htmlTransformer.toHtml(quizdown) +
        "<div><button type=\"submit\">Next</button></div>\n" +
        "</form>\n";
  }

  private HtmlTransformer transformerFor(String quizdown) {
    String questionType = extractQuestionType(quizdown);

    return switch (questionType) {
      case "{fib}" -> fillInTheBlank;
      case "{mc}" -> multipleChoiceTransformer;
      default -> throw new IllegalArgumentException("Unknown question type: " + questionType);
    };
  }

  private String extractQuestionType(String quizdown) {
    return new Scanner(quizdown).useDelimiter(" ").next().strip().toLowerCase();
  }
}
