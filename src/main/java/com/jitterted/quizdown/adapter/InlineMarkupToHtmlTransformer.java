package com.jitterted.quizdown.adapter;

/**
 * Transforms markup at the line-level (bold, italic, etc.),
 * doesn't understand cross-line transformations
 * (e.g., code fenced blocks)
 */
public class InlineMarkupToHtmlTransformer {

  public String toHtml(String quizdown) {
    quizdown = replaceBacktickWithCodeTag(quizdown);
    quizdown = handleBold(quizdown);
    quizdown = handleItalic(quizdown);

    return quizdown;
  }

  /**
   * As per Commonmark, Italic is single star or single underscore surrounding text
   * (stars must match stars, underscores must match underscores)
   */
  private String handleItalic(String quizdown) {
    // thanks to wietlol for these awesome regexes!
    return quizdown.replaceAll("([*_])(?<italic>.*?)\\1(?!\\1)", "<em>${italic}</em>");
  }

  /**
   * As per Commonmark, Bold is two stars or two underscores surrounding text
   * (stars must match stars, underscores must match underscores)
   */
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
