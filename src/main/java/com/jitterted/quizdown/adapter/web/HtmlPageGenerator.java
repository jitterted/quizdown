package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Question;
import org.springframework.stereotype.Service;

@Service
public class HtmlPageGenerator {
  private static final String HTML_HEADER = "<html>\n" +
      "<head>\n" +
      "<title>Hello</title>\n" +
      "</head>\n" +
      "<body>\n" +
      "<div>Hi there, <span th:text=\"${name}\">Text</span></div>\n";
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
