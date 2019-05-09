package com.jitterted.quizdown;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizdownProcessorFillInBlankToHtmlTest {

  @Test
  public void singleFibQuestionTransformedToHtmlForm() throws Exception {
    String quizdown = "{fib} If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";

    String html = new QuizdownProcessor().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("<form method='post' action='/answer'>\n" +
                       "  <label for=\"q1\">If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?</label>\n" +
                       "  <input type=\"text\" id=\"q1\" name=\"q1\" size=\"20\">\n" +
                       "<div><button type=\"submit\">Next</button></div>\n" +
                       "</form>\n");
  }

}
