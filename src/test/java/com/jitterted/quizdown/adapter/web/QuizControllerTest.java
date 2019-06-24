package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.port.repository.UserRepositoryMemoryAdapter;
import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.Response;
import com.jitterted.quizdown.domain.StubAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.DummyQuizCompletedNotifier;
import com.jitterted.quizdown.domain.port.QuizCompletedNotifier;
import org.junit.Test;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import static org.assertj.core.api.Assertions.assertThat;
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

  @Test
  public void htmlContainsChoiceWithCheckedAttributeFromAnswerService() throws Exception {
    // GIVEN an AnswerService that has a selected choice in a Response
    // for a specific user + question
    var questionStore = new QuestionStore();
    questionStore.create(QuestionType.MC, "Choose one:\n\n===\n\nA. first\nB. second\n", new DummyAnswerValidator());

    var userRepository = new UserRepositoryMemoryAdapter();
    User user = new User(new UserName("Hobbes"));
    Answer stubAnswer = new StubAnswer(false, Response.of("a"), 1);
    user.answered(stubAnswer);
    userRepository.save(user);

    var answerService = new AnswerService(questionStore, userRepository, new DummyQuizCompletedNotifier());
    var quizController = new QuizController(questionStore, new HtmlPageGenerator(new QuestionTransformer()), answerService);

    // WHEN generating the HTML page for that question
    String html = quizController.question("Hobbes", 1);

    // THEN expect the choice to have the "checked" attribute for the question
    assertThat(html)
        .contains("value=\"a\" checked/>\n",
                  "value=\"b\"/>\n");
  }
}