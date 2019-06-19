package com.jitterted.quizdown.domain;

import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import org.junit.Ignore;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class QuizServiceTest {
  @Ignore
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
