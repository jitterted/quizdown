package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.BlockHtmlTransformer;
import com.jitterted.quizdown.adapter.InlineMarkupToHtmlTransformer;
import org.springframework.web.util.HtmlUtils;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleChoiceTransformer implements HtmlTransformer {

  private static final Predicate<String> NOT_QUESTION_ANSWER_SEPARATOR
      = s -> !s.strip().equals("===");
  private final BlockHtmlTransformer blockHtmlTransformer = new BlockHtmlTransformer();
  private final InlineMarkupToHtmlTransformer inlineMarkupToHtmlTransformer = new InlineMarkupToHtmlTransformer();

  public String toHtml(String content) {
    content = HtmlUtils.htmlEscape(content);
    String questionText = extractQuestionText(content);
    String questionHtml = blockHtmlTransformer.transform(questionText);

    String choices = transformChoices(content);

    return questionHtml + choices;
  }

  private String extractQuestionText(String content) {
    return content.lines()
                  .takeWhile(NOT_QUESTION_ANSWER_SEPARATOR)
                  .collect(Collectors.joining("\n"));
  }

  private String transformChoices(String content) {
    Choice choice = new Choice();

    return content.lines()
                  .dropWhile(NOT_QUESTION_ANSWER_SEPARATOR)
                  .skip(1) // skip separator
                  .filter(Predicate.not(String::isBlank))
                  .map(inlineMarkupToHtmlTransformer::toHtml)
                  .map(choice::toHtml)
                  .collect(Collectors.joining("\n", "\n", "\n"));
  }

  public static class Choice implements HtmlTransformer {

    private int choiceNumber = 1;
    public static final String CHOICE_FORM_TEMPLATE = "<div class=\"field\">\n" +
        "  <div class=\"control\">\n" +
        "    <label class=\"control checkbox\">\n" +
        "      <input type=\"checkbox\" id=\"%1$s\" name=\"%1$s\" value=\"%2$s\"/>\n" +
        "      <span class=\"control-indicator\"></span>\n" +
        "      %3$s\n" +
        "    </label>\n" +
        "  </div>\n" +
        "</div>";

    public String toHtml(String choice) {
      choice = choice.strip();
      String choiceLetter = choice.substring(0, 1).toLowerCase();
      String choiceText = choice.substring(3);
      String id = "q1ch" + choiceNumber++;
      return String.format(CHOICE_FORM_TEMPLATE, id, choiceLetter, choiceText);
    }
  }
}
