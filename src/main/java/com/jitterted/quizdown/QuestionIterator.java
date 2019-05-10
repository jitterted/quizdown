package com.jitterted.quizdown;

import com.jitterted.quizdown.domain.Question;

import java.util.Iterator;
import java.util.List;

public class QuestionIterator implements Iterator<Question> {

  private final Iterator<Question> questionIterator;

  public QuestionIterator(List<Question> questions) {
    questionIterator = questions.iterator();
  }

  @Override
  public boolean hasNext() {
    return questionIterator.hasNext();
  }

  @Override
  public Question next() {
    return questionIterator.next();
  }
}
