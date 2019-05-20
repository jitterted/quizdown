package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealAnswer;
import org.junit.Test;

import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest {

  @Test
  public void convertsFormMapToMultipleChoiceWithSingleAnswer() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question1 = questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());

    AnswerService answerService = new AnswerService(questionStore);

    Map<String, String> map = Map.of("q1ch1", "a", "question", "1");

    answerService.process("Ted", map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question1, "a");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToMultipleChoiceWithMultipleAnswers() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question1 = questionStore.create(QuestionType.MC, "choose", new DummyAnswerValidator());

    Map<String, String> map = Map.of("q1ch1", "a",
                                     "q1ch4", "d",
                                     "question", "1");

    AnswerService answerService = new AnswerService(questionStore);

    answerService.process("Ted", map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question1, "a", "d");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToAnswerForFillInTheBlank() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question = questionStore.create(QuestionType.FIB,
                                             "blank",
                                             new DummyAnswerValidator());
    AnswerService answerService = new AnswerService(questionStore);

    Map<String, String> map = Map.of("q1", "response", "question", "1");
    answerService.process("Ted", map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new RealAnswer(question, "response");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);

  }

  @Test
  public void completedQuizProvidesGradedAnswers() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    AnswerService answerService = new AnswerService(questionStore);
    questionStore.create(QuestionType.FIB,
                         "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?",
                         new DefaultAnswerValidator(QuestionType.FIB, "map", "hashmap"));
    questionStore.create(QuestionType.MC,
                         "Choose A, B, or C?",
                         new DefaultAnswerValidator(QuestionType.MC, "a", "c"));

    // WHEN: we submit correct answers
    answerService.process("Ted", Map.of("q1", "map", "question", "1"));
    answerService.process("Ted", Map.of("q2ch1", "a",
                                        "q2ch3", "c",
                                        "question", "2"));

    // THEN: answers are correct

    GradedAnswerView fibExpectedAnswerView = new GradedAnswerView(1,
                                                                  "map",
                                                                  true);
    GradedAnswerView mcExpectedAnswerView = new GradedAnswerView(2,
                                                                 "a, c",
                                                                 true);

    assertThat(answerService.results())
        .containsExactly(fibExpectedAnswerView, mcExpectedAnswerView);

  }

}