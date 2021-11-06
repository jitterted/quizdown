package com.jitterted.quizdown.adapter.port.notifier;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
@Slf4j
public class QuizCompletedConsoleNotifier implements QuizCompletedNotifier {

    public QuizCompletedConsoleNotifier() {
        log.info("Console Notifier is active.");
    }

    @Override
    public void quizCompleted(User user) {
        System.out.println("Hey, " + user.name() + " has completed the Quiz!");
    }
}
