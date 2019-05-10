package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.AnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTransformerTest {

  @Test
  public void transformsQuestionIntoForm() throws Exception {
    Question question = new Question(
        QuestionType.FIB,
        "What's your name?",
        new AnswerValidator("jitterted"));

    String html = new QuestionTransformer().toHtml(question);

    assertThat(html)
        .isEqualTo("<form method='post' action='/answer'>\n" +
                       "  <label for=\"q1\">What's your name?</label>\n" +
                       "  <input type=\"text\" id=\"q1\" name=\"q1\" size=\"20\">\n" +
                       "<div><button type=\"submit\">Next</button></div>\n" +
                       "</form>\n");
  }
}