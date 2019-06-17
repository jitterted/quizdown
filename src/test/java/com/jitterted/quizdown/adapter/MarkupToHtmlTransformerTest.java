package com.jitterted.quizdown.adapter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarkupToHtmlTransformerTest {
  @Test
  public void angleBracketsProperlyEscapedToHtmlEntities() throws Exception {
    String quizdown = "|mc|B| Which of the following are *preferred* ways to create a list that can hold Strings (assume Java 8 or later)?\n" +
        "B. List<String> strings = new ArrayList<>();";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .contains("B. List&lt;String&gt; strings = new ArrayList&lt;&gt;();");
  }

  @Test
  public void backtickedTextIsSurroundedByHtmlCodeTags() throws Exception {
    String quizdown = "|mc|B| Which of the following are *preferred* ways to create a list that can hold Strings (assume Java 8 or later)?\n" +
        "\"A. `List` strings = new `ArrayList()`;\"";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .contains("A. <code style=\"background: none !important\" class=\"language-java\">List</code> strings = new <code style=\"background: none !important\" class=\"language-java\">ArrayList()</code>");
  }

  @Test
  public void singleWordWithTwoStarsOnBothSidesBecomesStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are **preferred** ways to create a list";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to create a list");
  }

  @Test
  public void multiWordPhraseSurroundedByTwoStarsBecomesStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are preferred ways to **create a list**?";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are preferred ways to <strong>create a list</strong>?");
  }

  @Test
  public void multipleBoldMarkupReplacedWithStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are **preferred** ways to **create a list**?";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to <strong>create a list</strong>?");
  }

  @Test
  public void doubleUnderscoreReplacedWithStrongHtmlElement() throws Exception {
    String quizdown = "Which of the following are __preferred__ ways to __create a list__?";

    String html = new MarkupToHtmlTransformer().toHtml(quizdown);

    assertThat(html)
        .isEqualTo("Which of the following are <strong>preferred</strong> ways to <strong>create a list</strong>?");
  }

}