package com.jitterted.quizdown.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

  @Test
  public void correctFillInBlankAnswerMarkedAsCorrect() throws Exception {
    AnswerValidator validator = new AnswerValidator("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "map");

    assertThat(answer.isCorrect())
        .isTrue();
  }

  @Test
  public void incorrectFillInBlankAnswerMarkedAsNotCorrect() throws Exception {
    AnswerValidator validator = new AnswerValidator("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "wrong");

    assertThat(answer.isCorrect())
        .isFalse();
  }
}
