package com.jitterted.quizdown.domain.port;

import com.jitterted.quizdown.domain.User;

public interface QuizCompletedNotifier {
  void quizCompleted(User user);
}
