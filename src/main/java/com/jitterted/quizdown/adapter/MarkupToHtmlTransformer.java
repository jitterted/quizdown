package com.jitterted.quizdown.adapter;

import org.springframework.web.util.HtmlUtils;

public class MarkupToHtmlTransformer {

  public String toHtml(String quizdown) {
    quizdown = HtmlUtils.htmlEscape(quizdown);

    quizdown = replaceBacktickWithCodeTag(quizdown);
    quizdown = handleBold(quizdown);

    return quizdown;
  }

  private String handleBold(String quizdown) {
    return quizdown.replaceAll("\\*\\*(.*?)\\*\\*", "<strong>$1</strong>");
  }

  private String replaceBacktickWithCodeTag(String content) {
    // style="background: none !important"
    return content.replaceAll("`(.*?)`", "<code style=\"background: none !important\" class=\\\"language-java\\\">$1</code>");
  }

}
