package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.port.UserRepositoryMemoryAdapter;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import org.junit.Test;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class QuizControllerTest {

  @Test
  public void notificationSentWhenQuizSessionDone() {
    var quizCompletedNotifier = mock(QuizCompletedNotifier.class);

    var userRepository = new UserRepositoryMemoryAdapter();
    UserName thanosUserName = new UserName("Thanos");
    userRepository.save(new User(thanosUserName));

    var answerService = new AnswerService(null, userRepository, quizCompletedNotifier);
    var quizController = new QuizController(null, null, answerService);

    var simpleSessionStatus = new SimpleSessionStatus();
    var redirectAttributes = new RedirectAttributesModelMap();
    quizController.quizSessionDone("Thanos", simpleSessionStatus, redirectAttributes);

    User notifiedUser = userRepository.findByName(thanosUserName).orElseThrow();
    verify(quizCompletedNotifier).quizCompleted(notifiedUser);
  }
}