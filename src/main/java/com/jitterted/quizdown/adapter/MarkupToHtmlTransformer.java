package com.jitterted.quizdown.adapter;

import org.springframework.web.util.HtmlUtils;

public class MarkupToHtmlTransformer {

  public String toHtml(String quizdown) {
    quizdown = HtmlUtils.htmlEscape(quizdown);

    quizdown = replaceBacktickWithCodeTag(quizdown);
    quizdown = handleBold(quizdown);
    quizdown = handleItalic(quizdown);

    return quizdown;
  }

  private String handleItalic(String quizdown) {
    // thanks to wietlol for these awesome regexes!
    return quizdown.replaceAll("([*_])(?<italic>.*?)\\1(?!\\1)", "<em>${italic}</em>");
  }

  private String handleBold(String quizdown) {
    // match either __ or **, but the right side needs to match the left side
    // hence the \1 on the right side of the capture group
    // something something negative lookahead with ?!
    return quizdown.replaceAll("([*_])\\1(?<bold>.*?)\\1\\1(?!\\1)", "<strong>${bold}</strong>");
  }

  private String replaceBacktickWithCodeTag(String content) {
    // style="background: none !important"
    return content.replaceAll("`(.*?)`", "<code style=\"background: none !important\" class=\\\"language-java\\\">$1</code>");
  }

}
