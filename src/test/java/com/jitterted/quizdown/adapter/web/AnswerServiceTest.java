package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerServiceTest {

  @Test
  public void convertsFormMapToMultipleChoiceWithSingleAnswer() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question1 = new Question(QuestionType.MC, "choose", new DummyAnswerValidator(), 1);
    questionStore.save(question1);

    Map<String, String> map = Map.of("q1ch1", "a", "question", "1");

    AnswerService answerService = new AnswerService(questionStore);

    answerService.process(map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new Answer(question1, "a");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToMultipleChoiceWithMultipleAnswers() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question1 = new Question(QuestionType.MC, "choose", new DummyAnswerValidator(), 1);
    questionStore.save(question1);

    Map<String, String> map = Map.of("q1ch1", "a",
                                     "q1ch4", "d",
                                     "question", "1");

    AnswerService answerService = new AnswerService(questionStore);

    answerService.process(map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new Answer(question1, "a", "d");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);
  }

  @Test
  public void convertsFormMapToAnswerForFillInTheBlank() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    Question question = new Question(QuestionType.FIB,
                                     "blank",
                                     new DummyAnswerValidator(),
                                     3);
    questionStore.save(question);
    AnswerService answerService = new AnswerService(questionStore);

    Map<String, String> map = Map.of("q3", "response", "question", "3");
    answerService.process(map);

    Set<Answer> answers = answerService.answers();
    assertThat(answers)
        .hasSize(1);

    Answer expectedAnswer = new Answer(question, "response");
    assertThat(answers.iterator().next())
        .isEqualTo(expectedAnswer);

  }

  @Test
  public void completedQuizProvidesGradedAnswers() throws Exception {
    Question question = new Question(QuestionType.FIB,
                                     "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?",
                                     new DefaultAnswerValidator(QuestionType.FIB, "map", "hashmap"),
                                     1);
    QuestionStore questionStore = new QuestionStore(List.of(question));
    AnswerService answerService = new AnswerService(questionStore);

    // WHEN: we submit correct answer
    answerService.process(Map.of("q1", "map", "question", "1"));

    // THEN: answer is correct
    Set<Answer> answers = answerService.answers();

    assertThat(answers.iterator().next().isCorrect())
        .isTrue();
  }


}