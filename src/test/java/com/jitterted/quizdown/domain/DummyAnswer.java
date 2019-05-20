package com.jitterted.quizdown.domain;

import java.util.Set;

public class DummyAnswer implements Answer {
  @Override
  public boolean isCorrect() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }

  @Override
  public Set<String> response() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }

  @Override
  public int questionNumber() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }
}
