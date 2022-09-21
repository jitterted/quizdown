package com.jitterted.quizdown.application;

import com.jitterted.quizdown.application.port.QuizCompletedNotifier;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class QuizServiceTest {

    @Test
    public void notificationSentWhenUserHasCompletedQuiz() throws Exception {
        User thanos = new User(new UserName("Thanos"));

        QuizCompletedNotifier quizCompletedNotifier = mock(QuizCompletedNotifier.class);

        // when: user has completed quiz
        QuizService quizService = new QuizService(quizCompletedNotifier);
        quizService.completedFor(thanos);

        // then: notification is sent
        verify(quizCompletedNotifier).quizCompleted(thanos);
    }

}
