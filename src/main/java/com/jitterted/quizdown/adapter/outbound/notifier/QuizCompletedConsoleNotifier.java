package com.jitterted.quizdown.adapter.outbound.notifier;

import com.jitterted.quizdown.application.port.QuizCompletedNotifier;
import com.jitterted.quizdown.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class QuizCompletedConsoleNotifier implements QuizCompletedNotifier {

    private static final Logger log = LoggerFactory.getLogger(QuizCompletedConsoleNotifier.class);

    public QuizCompletedConsoleNotifier() {
        log.info("Console Notifier is active.");
    }

    @Override
    public void quizCompleted(User user) {
        System.out.println("Hey, " + user.name() + " has completed the Quiz!");
    }
}
