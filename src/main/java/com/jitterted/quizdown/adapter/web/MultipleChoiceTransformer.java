package com.jitterted.quizdown.adapter.web;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleChoiceTransformer implements HtmlTransformer {

  public String toHtml(String content) {
    String questionText = extractQuestionText(content);

    String choices = transformChoices(content);

    return questionText + choices;
  }

  private String extractQuestionText(String content) {
    return content.lines()
                  .takeWhile(s -> !s.strip().equals("==="))
                  .collect(Collectors.joining("\n"));
  }

  private String transformChoices(String content) {
    Choice choice = new Choice();

    return content.lines()
                  .dropWhile(s -> !s.strip().equals("==="))
                  .skip(1) // skip separator
                  .filter(Predicate.not(String::isBlank))
                  .map(choice::toHtml)
                  .collect(Collectors.joining("\n", "\n", "\n"));
  }

  public static class Choice implements HtmlTransformer {

    private int choiceNumber = 1;

    public String toHtml(String choice) {
      choice = choice.strip();
      String template =
          "<div class=\"field\">\n" +
              "  <div class=\"control\">\n" +
              "    <label class=\"checkbox\">\n" +
              "      <input type=\"checkbox\" id=\"%1$s\" name=\"%1$s\" value=\"%2$s\"/>\n" +
              "      %3$s\n" +
              "    </label>\n" +
              "  </div>\n" +
              "</div>";
      String choiceLetter = choice.substring(0, 1).toLowerCase();
      String choiceText = choice.substring(3);
      String id = "q1ch" + choiceNumber++;
      return String.format(template, id, choiceLetter, choiceText);
    }
  }
}
