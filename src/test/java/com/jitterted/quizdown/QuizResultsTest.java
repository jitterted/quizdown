package com.jitterted.quizdown;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizResultsTest {

  @Test
  public void submittedCorrectAnswerIsGradedAsCorrect() throws Exception {
    Answers answers = new Answers();
    Answer answer = new Answer(null, null);
    answers.add(answer);

    QuizResults results = answers.results();

    assertThat(results.gradedAnswers().get(0).isCorrect())
        .isTrue();
  }

}
