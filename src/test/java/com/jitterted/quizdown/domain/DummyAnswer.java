package com.jitterted.quizdown.domain;

public class DummyAnswer implements Answer {
  @Override
  public boolean isCorrect() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }

  @Override
  public Response response() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }

  @Override
  public int questionNumber() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }

  @Override
  public Question question() {
    throw new UnsupportedOperationException("This is a dummy, you shouldn't be interacting with me.");
  }
}
