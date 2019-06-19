package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuizParser {

  private final QuestionParser questionParser = new QuestionParser();

  public QuestionStore parse(String quizdown) {
    Scanner scanner = questionDelimitedScanner(quizdown);

    List<Question> questions = scanner.tokens()
                                      .map(questionParser::parse)
                                      .collect(Collectors.toList());

    return new QuestionStore(questions);
  }

  public Scanner questionDelimitedScanner(String quizdown) {
    return new Scanner(quizdown).useDelimiter("\n---\n\n");
  }
}
