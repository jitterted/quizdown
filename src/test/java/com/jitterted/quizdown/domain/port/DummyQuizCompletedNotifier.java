package com.jitterted.quizdown.domain.port;

import com.jitterted.quizdown.domain.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("test")
public class DummyQuizCompletedNotifier implements QuizCompletedNotifier {
    @Override
    public void quizCompleted(User user) {
        throw new UnsupportedOperationException("Dummy can't do that.");
    }
}
