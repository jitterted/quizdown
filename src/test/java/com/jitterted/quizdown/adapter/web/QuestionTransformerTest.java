package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionTransformerTest {

  @Test
  public void transformsQuestionIntoForm() throws Exception {
    Question question = new Question(
        QuestionType.FIB,
        "What's your name?",
        DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("jitterted"),
        73);

    String html = new QuestionTransformer().toHtml(question, Response.of());

    assertThat(html)
        .isEqualTo("<form method='post' action='/answer'>\n" +
                       "  <p class=\"question\">What's your name?</p>\n" +
                       "  <div class=\"field\">\n" +
                       "    <div class=\"control\">\n" +
                       "      <input class=\"input\" type=\"text\" id=\"q\" name=\"q\" size=\"20\" value=\"\">\n" +
                       "    </div>\n" +
                       "  </div>\n" +
                       "  <input type=\"hidden\" id=\"question\" name=\"question\" value=\"73\">\n" +
                       "  <br/>\n" +
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

    String html = new QuestionTransformer().toHtml(question, Response.of());

    assertThat(html)
        .contains("<a class=\"button\" disabled>Previous</a>");
  }

  @Test
  public void questionWithNonEmptyMultipleChoiceResponseSelectsChoice() throws Exception {
    Question question = new Question(
        QuestionType.MC,
        "Choose your favorite Java keywords:\n" +
            "\n" +
            "===\n" +
            "\n" +
            "A. final\n" +
            "\n" +
            "B. var\n",
        new DummyAnswerValidator(),
        2);

    String html = new QuestionTransformer().toHtml(question, Response.of("b"));

    assertThat(html)
        .contains("value=\"b\" checked/>\n");
  }

  @Test
  public void fibWithNonEmptyResponsePrefillsTextfield() throws Exception {
    Question question = new Question(QuestionType.FIB,
                                     "Favorite Collection class",
                                     new DummyAnswerValidator(),
                                     3);

    String html = new QuestionTransformer().toHtml(question, Response.of("arraylist"));

    assertThat(html)
        .contains("value=\"arraylist\">");
  }
}