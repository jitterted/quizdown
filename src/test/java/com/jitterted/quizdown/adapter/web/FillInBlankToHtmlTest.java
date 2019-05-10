package com.jitterted.quizdown.adapter.web;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FillInBlankToHtmlTest {

  @Test
  public void fibQuestionContentTransformedToHtmlForm() throws Exception {
    String fib = "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";

    String html = new FillInTheBlankTransformer().toHtml(fib);

    assertThat(html)
        .isEqualTo("  <label for=\"q1\">If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?</label>\n" +
                       "  <input type=\"text\" id=\"q1\" name=\"q1\" size=\"20\">\n");
  }

}
