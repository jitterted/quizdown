package com.jitterted.quizdown;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.Answers;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswersQuizResultsTest {

  @Test
  @Ignore
  public void submittedCorrectAnswerIsGradedAsCorrect() throws Exception {
    Answers answers = new Answers();
    Answer answer = new Answer(null, null);
    answers.add(answer);

    QuizResults results = answers.results();

    assertThat(results.gradedAnswers().get(0).isCorrect())
        .isTrue();
  }

}
