package com.jitterted.quizdown.adapter.port;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import org.springframework.stereotype.Service;

@Service
public class QuizCompletedConsoleNotifier implements QuizCompletedNotifier {
  @Override
  public void quizCompleted(User user) {
    System.out.println("Hey, " + user.name() + " has completed the Quiz!");
  }
}
