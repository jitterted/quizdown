package com.jitterted.quizdown.adapter.web;

public class FillInTheBlankTransformer implements HtmlTransformer {

  private int questionNumber = 1;

  @Override
  public String toHtml(String content) {
    String template =
        "  <label for=\"q%1$d\">%2$s</label>\n" +
            "  <input type=\"text\" id=\"q%1$d\" name=\"q%1$d\" size=\"20\">\n";

    return String.format(template, questionNumber++, content);
  }
}
