package com.jitterted.quizdown;

import java.util.Iterator;
import java.util.List;

public class QuestionStore {
  private List<Question> questions;

  public QuestionStore(String quizdown) {
    // parse into list
  }

  public Iterator<Question> iterator() {
    return questions.iterator();
  }

}
