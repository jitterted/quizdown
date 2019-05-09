package com.jitterted.quizdown;

import java.util.Iterator;
import java.util.Scanner;

public class QuestionIterator implements Iterator<String> {

  private final Iterator<String> questionIterator;
  private QuizdownProcessor quizdownProcessor = new QuizdownProcessor();

  public QuestionIterator(String quizdown) {
    questionIterator = new Scanner(quizdown).useDelimiter("\n---\n\n");
  }

  @Override
  public boolean hasNext() {
    return questionIterator.hasNext();
  }

  @Override
  public String next() {
    return quizdownProcessor.toHtml(questionIterator.next());
  }
}
