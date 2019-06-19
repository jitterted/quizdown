package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.web.MultipleChoiceTransformer.Choice;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MultipleChoiceToHtmlTest {

  @Test
  public void convertsIndividualChoiceToHtml() throws Exception {
    String choice = "A. First choice";

    String html = new Choice().toHtml(choice);

    assertThat(html)
        .isEqualTo(
            "<div class=\"field\">\n" +
                "  <div class=\"control\">\n" +
                "    <label class=\"checkbox\">\n" +
                "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                "      First choice\n" +
                "    </label>\n" +
                "  </div>\n" +
                "</div>"
        );
  }

  @Test
  public void convertSecondChoiceToHtmlWithChoiceIdOf2() throws Exception {
    Choice choice = new Choice();
    choice.toHtml("one"); // skip to choice 2

    String html = choice.toHtml("B. Second choice");

    assertThat(html)
        .isEqualTo("<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "      Second choice\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>"
        );
  }

  @Test
  public void multipleChoiceConvertsToHtml() throws Exception {
    String mc = "<p>Choose your favorite Java keywords:</p>\n" +
        "\n" +
        "===\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n" +
        "\n" +
        "C. volatile\n" +
        " \n" +
        "D. switch \n";

    String html = new MultipleChoiceTransformer().toHtml(mc);

    assertThat(html)
        .isEqualTo("<p>Choose your favorite Java keywords:</p>\n" +
                       "\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                       "      final\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "      var\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch3\" name=\"q1ch3\" value=\"c\"/>\n" +
                       "      volatile\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch4\" name=\"q1ch4\" value=\"d\"/>\n" +
                       "      switch\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n"
        );
  }

}
