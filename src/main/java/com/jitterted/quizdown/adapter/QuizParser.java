package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.Question;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class QuizParser {

  private final QuestionParser questionParser = new QuestionParser();

  public List<Question> parse(String quizdown) {
    Scanner scanner = new Scanner(quizdown).useDelimiter("\n---\n\n");

    return scanner.tokens()
                  .map(questionParser::parse)
                  .collect(Collectors.toList());
  }
}
