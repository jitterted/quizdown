package com.jitterted.quizdown.application;

import com.jitterted.quizdown.application.port.QuizCompletedNotifier;
import com.jitterted.quizdown.domain.User;

public class QuizService {

    private final QuizCompletedNotifier quizCompletedNotifier;

    public QuizService(QuizCompletedNotifier quizCompletedNotifier) {
        this.quizCompletedNotifier = quizCompletedNotifier;
    }

    public void completedFor(User user) {
        quizCompletedNotifier.quizCompleted(user);
    }
}
