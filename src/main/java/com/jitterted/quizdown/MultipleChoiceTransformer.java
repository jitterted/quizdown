package com.jitterted.quizdown;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleChoiceTransformer implements HtmlTransformer {

  public String toHtml(String mc) {
    Choice choice = new Choice();

    String choices = mc.lines()
                       .skip(1)
                       .filter(Predicate.not(String::isBlank))
                       .map(String::strip)
                       .map(choice::toHtml)
                       .collect(Collectors.joining("\n", "\n", "\n"));

    String questionText = extractQuestionFrom(mc);

    return String.format("<p>%s</p>", questionText) + choices;
  }

  private String extractQuestionFrom(String mc) {
    return mc.lines().findFirst().get().substring(5);
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
