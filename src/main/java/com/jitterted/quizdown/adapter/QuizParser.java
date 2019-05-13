package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.QuestionStore;

import java.util.Scanner;

public class QuizParser {

  private final QuestionParser questionParser = new QuestionParser();

  public QuestionStore parse(String quizdown) {
    Scanner scanner = new Scanner(quizdown).useDelimiter("\n---\n\n");

    return new QuestionStore();
//    return scanner.tokens()
//                  .map(questionParser::parse)
//                  .collect(Collectors.toList());
  }
}
