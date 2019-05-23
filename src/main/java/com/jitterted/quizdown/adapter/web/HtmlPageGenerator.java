package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import org.springframework.stereotype.Service;

@Service
public class HtmlPageGenerator {
  private static final String HTML_HEADER =
      "<!DOCTYPE html>\n" +
          "<html>\n" +
      "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
          "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.min.css\">" +
      "  <link rel=\"stylesheet\" th:href=\"@{/prism.css}\"/>\n" +
      "  <script th:src=\"@{/prism.js}\"></script>\n" +
          "  <title>Hello</title>\n" +
      "</head>\n" +
      "<body>\n" +
      "<div>Hi there, <span th:text=\"${name}\">Name</span></div>\n";
  private static final String HTML_FOOTER = "</body>\n" +
      "</html>\n";

  private final QuestionTransformer questionTransformer;

  public HtmlPageGenerator(QuestionTransformer questionTransformer) {
    this.questionTransformer = questionTransformer;
  }

  public String forQuestion(Question question) {
    return HtmlPageGenerator.HTML_HEADER +
        questionTransformer.toHtml(question) +
        HtmlPageGenerator.HTML_FOOTER;
  }

}
