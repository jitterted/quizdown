package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.BlockHtmlTransformer;
import com.jitterted.quizdown.adapter.InlineMarkupToHtmlTransformer;
import com.jitterted.quizdown.domain.Response;
import org.springframework.web.util.HtmlUtils;

import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MultipleChoiceTransformer implements HtmlTransformer {

    private static final Predicate<String> NOT_QUESTION_ANSWER_SEPARATOR
            = s -> !s.strip().equals("===");
    private final BlockHtmlTransformer blockHtmlTransformer = new BlockHtmlTransformer();
    private final InlineMarkupToHtmlTransformer inlineMarkupToHtmlTransformer = new InlineMarkupToHtmlTransformer();

    public String toHtml(String content, Response response) {
        content = HtmlUtils.htmlEscape(content);
        String questionText = extractQuestionText(content);
        String questionHtml = blockHtmlTransformer.transform(questionText);

        String choices = transformChoices(content, response);

        return questionHtml + choices;
    }

    private String extractQuestionText(String content) {
        return content.lines()
                .takeWhile(NOT_QUESTION_ANSWER_SEPARATOR)
                .collect(Collectors.joining("\n"));
    }

    private String transformChoices(String content, Response response) {
        Choice choice = new Choice();

        return content.lines()
                .dropWhile(NOT_QUESTION_ANSWER_SEPARATOR)
                .skip(1) // skip separator
                .filter(Predicate.not(String::isBlank))
                .map(inlineMarkupToHtmlTransformer::toHtml)
                .map(ch -> choice.toHtml(ch, response))
                .collect(Collectors.joining("\n", "\n", "\n"));
    }

    public static class Choice implements HtmlTransformer {

        private int choiceNumber = 1;
        private static final String CHOICE_FORM_TEMPLATE =
                "<div class=\"field\">\n" +
                        "  <div class=\"control\">\n" +
                        "    <label class=\"control checkbox\">\n" +
                        "      <input type=\"checkbox\" id=\"%1$s\" name=\"%1$s\" value=\"%2$s\"%3$s/>\n" +
                        "      <span class=\"control-indicator\"></span>\n" +
                        "      %4$s\n" +
                        "    </label>\n" +
                        "  </div>\n" +
                        "</div>";

        public String toHtml(String choice, Response response) {
            String id = "q1ch" + choiceNumber++;

            choice = choice.strip();
            String choiceLetter = choice.substring(0, 1).toLowerCase();
            boolean choiceSelected = Response.of(choiceLetter).matchesAny(response);
            String choiceCheckedText = choiceSelected ? " checked" : "";
            String choiceText = choice.substring(3);
            return String.format(CHOICE_FORM_TEMPLATE, id, choiceLetter, choiceCheckedText, choiceText);
        }
    }
}
