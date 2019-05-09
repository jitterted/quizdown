package com.jitterted.quizdown;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizdownProcessorMultipleChoiceToHtmlTest {

  @Test
  public void oneMultipleChoiceQuestionIsConvertedToHtmlForm() throws Exception {

    String quizdown = "{mc} Choose your favorite Java keywords:\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n" +
        "\n" +
        "C. volatile\n" +
        " \n" +
        "D. switch \n";

    String html = new QuizdownProcessor().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("<form method='post' action='/answer'>\n" +
                       "<p>Choose your favorite Java keywords:</p>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\"/>\n" +
                       "  <label for=\"q1ch1\">final</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\"/>\n" +
                       "  <label for=\"q1ch2\">var</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch3\" name=\"q1ch3\"/>\n" +
                       "  <label for=\"q1ch3\">volatile</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch4\" name=\"q1ch4\"/>\n" +
                       "  <label for=\"q1ch4\">switch</label>\n" +
                       "</div>\n" +
                       "<div><button type=\"submit\">Next</button></div>\n" +
                       "</form>\n");
  }
}
