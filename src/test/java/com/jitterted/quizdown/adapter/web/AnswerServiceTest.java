package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.port.InMemoryUserRepository;
import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.junit.Test;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest {

  @Test
  public void answerForUnknownUserAutoCreatesUser() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    QuestionStore questionStore = new QuestionStore();
    questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());
    AnswerService answerService = new AnswerService(questionStore, userRepository);

    Map<String, String> answerMap = Map.of("q1ch1", "a", "question", "1");

    answerService.processAnswer("Iron", answerMap);

    Optional<User> iron = userRepository.findByName(new UserName("Iron"));

    assertThat(iron)
        .isNotNull();
  }

  @Test
  public void answersForOneUserNotRetrievedByAnotherUser() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    userRepository.save(new User(new UserName("Ted")));
    userRepository.save(new User(new UserName("wietlol")));

    QuestionStore questionStore = new QuestionStore();
    questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());
    AnswerService answerService = new AnswerService(questionStore, userRepository);

    Map<String, String> wietlolMap = Map.of("q1ch1", "a", "question", "1");
    answerService.processAnswer("wietlol", wietlolMap);

    Map<String, String> tedMap = Map.of("q1ch4", "d", "question", "1");
    answerService.processAnswer("Ted", tedMap);

    assertThat(answerService.answersFor("wietlol").iterator().next().response())
        .containsOnly("a");
    assertThat(answerService.answersFor("Ted").iterator().next().response())
        .containsOnly("d");
  }

  @Test
  public void convertsFormMapToMultipleChoiceWithSingleAnswer() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    userRepository.save(new User(new UserName("wietlol")));
    QuestionStore questionStore = new QuestionStore();
    Question question1 = questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());

    AnswerService answerService = new AnswerService(questionStore, userRepository);

    Map<String, String> map = Map.of("q1ch1", "a", "question", "1");

    answerService.processAnswer("wietlol", map);

    Set<Answer> answers = answerService.answersFor("wietlol");
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question1, "a");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToMultipleChoiceWithMultipleAnswers() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    userRepository.save(new User(new UserName("Ted")));
    QuestionStore questionStore = new QuestionStore();
    Question question1 = questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());

    Map<String, String> map = Map.of("q1ch1", "a",
                                     "q1ch4", "d",
                                     "question", "1");

    AnswerService answerService = new AnswerService(questionStore, userRepository);

    answerService.processAnswer("Ted", map);

    Set<Answer> answers = answerService.answersFor("Ted");
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question1, "a", "d");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToAnswerForFillInTheBlank() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    userRepository.save(new User(new UserName("Ted")));
    QuestionStore questionStore = new QuestionStore();
    Question question = questionStore.create(QuestionType.FIB,
                                             "blank",
                                             new DummyAnswerValidator());
    AnswerService answerService = new AnswerService(questionStore, userRepository);

    Map<String, String> map = Map.of("q1", "response", "question", "1");
    answerService.processAnswer("Ted", map);

    Set<Answer> answers = answerService.answersFor("Ted");
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question, "response");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);

  }

  @Test
  public void completedQuizProvidesGradedAnswers() throws Exception {
    UserRepository userRepository = new InMemoryUserRepository();
    userRepository.save(new User(new UserName("Ted")));
    QuestionStore questionStore = new QuestionStore();
    AnswerService answerService = new AnswerService(questionStore, userRepository);
    questionStore.create(QuestionType.FIB,
                         "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?",
                         new DefaultAnswerValidator(QuestionType.FIB, "map", "hashmap"));
    questionStore.create(QuestionType.MC,
                         "Choose A, B, or C?",
                         new DefaultAnswerValidator(QuestionType.MC, "a", "c"));

    // WHEN: we submit correct answers
    answerService.processAnswer("Ted", Map.of("q1", "map", "question", "1"));
    answerService.processAnswer("Ted", Map.of("q2ch1", "a",
                                              "q2ch3", "c",
                                              "question", "2"));

    // THEN: answers are correct

    GradedAnswerView fibExpectedAnswerView = new GradedAnswerView(1,
                                                                  "map",
                                                                  true);
    GradedAnswerView mcExpectedAnswerView = new GradedAnswerView(2,
                                                                 "a, c",
                                                                 true);

    assertThat(GradedAnswerView.toResultsView(answerService.answersFor("Ted")))
        .containsExactly(fibExpectedAnswerView, mcExpectedAnswerView);

  }

}