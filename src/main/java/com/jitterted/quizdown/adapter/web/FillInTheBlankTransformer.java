package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Response;

public class FillInTheBlankTransformer implements HtmlTransformer {

  private int questionNumber = 1;

  @Override
  public String toHtml(String content, Response response) {
    String template =
        "  <p class=\"question\">%2$s</p>\n" +
            "    <div class=\"field\">\n" +
            "      <div class=\"control\">\n" +
            "        <input class=\"input\" type=\"text\" id=\"q%1$d\" name=\"q%1$d\" size=\"20\" value=\"%3$s\">\n" +
            "      </div>\n" +
            "  </div>\n";

    String value = String.join("", response.asSet());
    return String.format(template, questionNumber++, content, value);
  }
}
