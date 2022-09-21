package com.jitterted.quizdown.application.port;

import com.jitterted.quizdown.domain.User;

public interface QuizCompletedNotifier {
    void quizCompleted(User user);
}
