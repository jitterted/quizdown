package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.port.repository.FakeUserRepository;
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
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.support.SimpleSessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.Collections;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class QuizControllerTest {

  @Test
  public void notificationSentWhenQuizSessionDone() {
    var quizCompletedNotifier = mock(QuizCompletedNotifier.class);

    var userRepository = new FakeUserRepository();
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

    var userRepository = new FakeUserRepository();
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

  @Test
  public void answerRedirectsToQuestionWhenThereAreMoreQuestions() throws Exception {
    // GIVEN question store has 2 questions
    var nullAnswerService = mock(AnswerService.class);
    var questionStore = new QuestionStore();
    questionStore.create(QuestionType.FIB, "question1", new DummyAnswerValidator());
    questionStore.create(QuestionType.FIB, "question2", new DummyAnswerValidator());
    var quizController = new QuizController(questionStore, null, nullAnswerService);

    //
    String viewName = quizController.answer(new RedirectAttributesModelMap(),
                                            Collections.emptyMap(), 1, "name");

    assertThat(viewName)
        .isEqualTo("redirect:/question");
  }

  @Test
  public void answerRedirectsToDoneWhenNoMoreQuestionsLeft() throws Exception {
    // GIVEN question store has 2 questions
    var nullAnswerService = mock(AnswerService.class);
    var questionStore = new QuestionStore();
    questionStore.create(QuestionType.FIB, "question1", new DummyAnswerValidator());
    questionStore.create(QuestionType.FIB, "question2", new DummyAnswerValidator());
    var quizController = new QuizController(questionStore, null, nullAnswerService);

    // WHEN we post an answer to the 2nd question
    String viewName = quizController.answer(new RedirectAttributesModelMap(),
                                            Collections.emptyMap(), 2, "name");

    // THEN we expect to be redirected to the confirmation that the user wants to complete the quiz
    assertThat(viewName)
        .isEqualTo("redirect:/confirm-finished");
  }

}