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
      String template = "<div>\n" +
          "  <input type=\"checkbox\" id=\"%1$s\" name=\"%1$s\"/>\n" +
          "  <label for=\"%1$s\">%2$s</label>\n" +
          "</div>";
      String choiceText = choice.substring(3);
      String id = "q1ch" + choiceNumber++;
      return String.format(template, id, choiceText);
    }
  }
}
