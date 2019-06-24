package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.Response;
import org.springframework.stereotype.Service;

@Service
public class HtmlPageGenerator {
  private static final String HTML_HEADER =
      "<!DOCTYPE html>\n" +
          "<html>\n" +
      "<head>\n" +
          "  <meta charset=\"utf-8\">\n" +
          "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
          "  <link rel=\"stylesheet\" href=\"https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.5/css/bulma.min.css\">\n" +
      "  <link rel=\"stylesheet\" th:href=\"@{/prism.css}\"/>\n" +
          "  <link rel=\"stylesheet\" th:href=\"@{/simple-forms.css}\"/>\n" +
      "  <script th:src=\"@{/prism.js}\"></script>\n" +
          "  <title>Hello</title>\n" +
      "</head>\n" +
      "<body>\n" +
          "  <div class=\"columns\">\n" +
          "    <div class=\"column is-half is-offset-one-quarter\">\n" +
          "      <div>Hi there, <span th:text=\"${name}\">Name</span></div>\n";

  private static final String HTML_FOOTER =
      "    </div>\n" +
          "  </div>\n" +
          "</body>\n" +
      "</html>\n";

  private final QuestionTransformer questionTransformer;

  public HtmlPageGenerator(QuestionTransformer questionTransformer) {
    this.questionTransformer = questionTransformer;
  }

  public String forQuestion(Question question, Response response) {
    return HtmlPageGenerator.HTML_HEADER +
        questionTransformer.toHtml(question, response) +
        HtmlPageGenerator.HTML_FOOTER;
  }

}
