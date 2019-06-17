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
    // match either __ or **, but the right side needs to match the left side
    // hence the \1 on the right side of the capture group
    return quizdown.replaceAll("(__|\\*\\*)(?<content>.*?)\\1", "<strong>${content}</strong>");
  }

  private String replaceBacktickWithCodeTag(String content) {
    // style="background: none !important"
    return content.replaceAll("`(.*?)`", "<code style=\"background: none !important\" class=\\\"language-java\\\">$1</code>");
  }

}
