package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.DefaultAnswerValidator;
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
        DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("jitterted"),
        73);

    String html = new QuestionTransformer().toHtml(question);

    assertThat(html)
        .isEqualTo("<form method='post' action='/answer'>\n" +
                       "  <label for=\"q1\">What's your name?</label>\n" +
                       "  <input type=\"text\" id=\"q1\" name=\"q1\" size=\"20\">\n" +
                       "  <input type=\"hidden\" id=\"question\" name=\"question\" value=\"73\">\n" +
                       "  <div class=\"field\">\n" +
                       "    <div class=\"control\">\n" +
                       "      <a class=\"button\" href=\"/question?question=72\">Previous</a>\n" +
                       "      <button class=\"button is-link\">\n" +
                       "        Next Question\n" +
                       "      </button>\n" +
                       "    </div>\n" +
                       "  </div>\n" +
                       "</form>\n");
  }

  @Test
  public void firstQuestionDisablesPreviousLink() throws Exception {
    Question question = new Question(
        QuestionType.FIB,
        "What's your name?",
        DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("jitterted"),
        1); // first question!

    String html = new QuestionTransformer().toHtml(question);

    assertThat(html)
        .contains("<a class=\"button\" disabled>Previous</a>");
  }
}