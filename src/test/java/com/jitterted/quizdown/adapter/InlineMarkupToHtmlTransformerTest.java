package com.jitterted.quizdown.adapter;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class InlineMarkupToHtmlTransformerTest {

  @Test
  public void backtickedTextIsSurroundedByHtmlCodeTags() throws Exception {
    String quizdown = "|mc|B| Which of the following are *preferred* ways to create a list that can hold Strings (assume Java 8 or later)?\n" +
        "\"A. `List` strings = new `ArrayList()`;\"";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .contains("A. <code style=\"background: none !important\" class=\"language-java\">List</code> strings = new <code style=\"background: none !important\" class=\"language-java\">ArrayList()</code>");
  }

  @Test
  public void singleWordWithTwoStarsOnBothSidesBecomesStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are **preferred** ways to create a list";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to create a list");
  }

  @Test
  public void multiWordPhraseSurroundedByTwoStarsBecomesStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are preferred ways to **create a list**?";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are preferred ways to <strong>create a list</strong>?");
  }

  @Test
  public void multipleBoldMarkupReplacedWithStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are **preferred** ways to **create a list**?";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to <strong>create a list</strong>?");
  }

  @Test
  public void doubleUnderscoreReplacedWithStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are __preferred__ ways to __create a list__?";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to <strong>create a list</strong>?");
  }

  @Test
  public void singleStarReplacedWithEmphasizedHtmlElement() throws Exception {
    String quizdown = "Which of the following are _preferred_ ways to create a List?";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <em>preferred</em> ways to create a List?");
  }

  @Test
  public void mixtureOfBoldAndItalicReplacedProperly() throws Exception {
    String quizdown = "***Which*** of the _**following**_ are _preferred_ ways to ___create___ a __List__?";

    String html = new InlineMarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("<strong><em>Which</em></strong> of the <em><strong>following</strong></em> are <em>preferred</em> ways to <strong><em>create</em></strong> a <strong>List</strong>?");
  }

}