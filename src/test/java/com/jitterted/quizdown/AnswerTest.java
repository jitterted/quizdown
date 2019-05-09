package com.jitterted.quizdown;

import lombok.NonNull;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerTest {

  @Test
  public void correctAnswerIsCorrect() throws Exception {
    @NonNull AnswerValidator validator = new AnswerValidator("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "map");

    assertThat(answer.isCorrect())
        .isTrue();
  }

  @Test
  public void incorrectAnswerIsNotCorrect() throws Exception {
    @NonNull AnswerValidator validator = new AnswerValidator("map", "hashmap");
    Question fibQuestion = new Question(QuestionType.FIB, "", validator);
    Answer answer = new Answer(fibQuestion, "wrong");

    assertThat(answer.isCorrect())
        .isFalse();
  }
}
