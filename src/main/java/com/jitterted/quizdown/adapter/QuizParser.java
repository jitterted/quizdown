package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuizParser {

  private final QuestionParser questionParser = new QuestionParser();

  public QuestionStore parse(String quizdown) {
    Scanner scanner = new Scanner(quizdown).useDelimiter("\n---\n\n");

    List<Question> questions = scanner.tokens()
                                      .map(questionParser::parse)
                                      .collect(Collectors.toList());

    return new QuestionStore(questions);
  }
}
