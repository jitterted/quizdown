package com.jitterted.quizdown.domain;

import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;

public class QuizService {

    private final QuizCompletedNotifier quizCompletedNotifier;

    public QuizService(QuizCompletedNotifier quizCompletedNotifier) {
        this.quizCompletedNotifier = quizCompletedNotifier;
    }

    public void completedFor(User user) {
        quizCompletedNotifier.quizCompleted(user);
    }
}
