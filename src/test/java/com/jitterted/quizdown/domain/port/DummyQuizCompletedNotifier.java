package com.jitterted.quizdown.domain.port;

import com.jitterted.quizdown.domain.User;

public class DummyQuizCompletedNotifier implements QuizCompletedNotifier {
  @Override
  public void quizCompleted(User user) {
    throw new UnsupportedOperationException("Dummy can't do that.");
  }
}
