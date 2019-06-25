package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Response;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FillInBlankToHtmlTest {

  @Test
  public void contentTransformedToHtmlFormForNotPreviouslyAnsweredQuestion() throws Exception {
    String fib = "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";

    String html = new FillInTheBlankTransformer().toHtml(fib, Response.of());

    assertThat(html)
        .isEqualTo("  <label for=\"q1\">If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?</label>\n" +
                       "  <input type=\"text\" id=\"q1\" name=\"q1\" size=\"20\" value=\"\">\n");
  }

  @Test
  public void formInputHasValueFromPreviousResponse() throws Exception {
    String fib = "What is your favorite Java collection?";

    String html = new FillInTheBlankTransformer().toHtml(fib, Response.of("queue"));

    assertThat(html)
        .contains("value=\"queue\">");
  }

}
