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
        .isEqualTo("<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                       "  <label for=\"q1ch1\">First choice</label>\n" +
                       "</div>");
  }

  @Test
  public void convertSecondChoiceToHtmlWithChoiceIdOf2() throws Exception {
    Choice choice = new Choice();
    choice.toHtml("one");

    String html = choice.toHtml("B. Second choice");

    assertThat(html)
        .isEqualTo("<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "  <label for=\"q1ch2\">Second choice</label>\n" +
                       "</div>");
  }

  @Test
  public void multipleChoiceConvertsToHtml() throws Exception {
    String mc = "Choose your favorite Java keywords:\n" +
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
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                       "  <label for=\"q1ch1\">final</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "  <label for=\"q1ch2\">var</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch3\" name=\"q1ch3\" value=\"c\"/>\n" +
                       "  <label for=\"q1ch3\">volatile</label>\n" +
                       "</div>\n" +
                       "<div>\n" +
                       "  <input type=\"checkbox\" id=\"q1ch4\" name=\"q1ch4\" value=\"d\"/>\n" +
                       "  <label for=\"q1ch4\">switch</label>\n" +
                       "</div>\n");
  }

}
