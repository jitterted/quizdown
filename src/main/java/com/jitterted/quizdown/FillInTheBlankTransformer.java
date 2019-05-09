package com.jitterted.quizdown;

public class FillInTheBlankTransformer implements HtmlTransformer {

  private int questionNumber = 1;

  @Override
  public String toHtml(String fibText) {
    String template =
        "  <label for=\"q%1$d\">%2$s</label>\n" +
            "  <input type=\"text\" id=\"q%1$d\" name=\"q%1$d\" size=\"20\">\n";

    String questionText = fibText.substring(6);

    return String.format(template, questionNumber++, questionText);
  }
}
