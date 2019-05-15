package com.jitterted.quizdown.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

  @Test
  public void correctFillInBlankAnswerMarkedAsCorrect() throws Exception {
    DefaultAnswerValidator validator = DefaultAnswerValidator
        .forType(QuestionType.FIB)
        .correctChoices("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "map");

    assertThat(answer.isCorrect())
        .isTrue();
  }

  @Test
  public void incorrectFillInBlankAnswerMarkedAsNotCorrect() throws Exception {
    DefaultAnswerValidator validator = DefaultAnswerValidator
        .forType(QuestionType.FIB)
        .correctChoices("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "wrong");

    assertThat(answer.isCorrect())
        .isFalse();
  }

  @Test
  public void correctMultipleChoiceAnswerMarkedAsCorrect() throws Exception {
    DefaultAnswerValidator mcValidator = DefaultAnswerValidator.forType(QuestionType.MC)
                                                               .correctChoices("a", "b");

    Question question = new Question(QuestionType.MC, "choose wisely", mcValidator);

    Answer answer = new Answer(question, "a", "b");

    assertThat(answer.isCorrect())
        .isTrue();
  }

  @Test
  public void incorrectMultipleChoiceAnswerMarkedAsNotCorrect() throws Exception {
    DefaultAnswerValidator mcValidator = DefaultAnswerValidator.forType(QuestionType.MC)
                                                               .correctChoices("a", "b");

    Question question = new Question(QuestionType.MC, "choose poorly", mcValidator);

    Answer answer = new Answer(question, "b");

    assertThat(answer.isCorrect())
        .isFalse();
  }
}
