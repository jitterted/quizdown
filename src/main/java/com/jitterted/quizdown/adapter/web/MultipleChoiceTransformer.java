package com.jitterted.quizdown.adapter.web;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleChoiceTransformer implements HtmlTransformer {

  public String toHtml(String content) {
    Choice choice = new Choice();

    String choices = content.lines()
                            .skip(1)
                            .filter(Predicate.not(String::isBlank))
                            .map(String::strip)
                            .map(choice::toHtml)
                            .collect(Collectors.joining("\n", "\n", "\n"));

    String questionText = content.lines().findFirst().orElse("?? no question text ??");

    return String.format("<p>%s</p>", questionText) + choices;
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
