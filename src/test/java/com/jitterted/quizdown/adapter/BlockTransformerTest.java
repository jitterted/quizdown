package com.jitterted.quizdown.adapter;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BlockTransformerTest {

  @Test
  public void onePlainBlockConvertedToSingleParagraphElement() throws Exception {
    String quizdown = "What is your name?\n";

    String html = new BlockTransformer().transform(quizdown);

    assertThat(html)
        .isEqualTo("<p>What is your name?</p>\n");
  }

  @Test
  public void onePlainBlockWithTwoLinesConvertedToSingleParagraphElement() throws Exception {
    // What is your name?
    // Still part of the same block.
    String quizdown = "What is your name?\n" +
        "Still part of the same block.\n";

    String html = new BlockTransformer().transform(quizdown);

    assertThat(html)
        .isEqualTo("<p>What is your name?\n" +
                       "Still part of the same block.</p>\n");
  }

  @Test
  public void twoPlainBlocksConvertedToTwoParagraphElements() throws Exception {
    String quizdown = "Take a look at these two classes:\n" +
        "\n" +
        "What's wrong with them?\n";

    String html = new BlockTransformer().transform(quizdown);

    assertThat(html)
        .isEqualTo("<p>Take a look at these two classes:</p>\n" +
                       "<p>What's wrong with them?</p>\n");
  }

  @Test
  public void codeFencedBlockWithoutBlanksConvertedToPreElement() throws Exception {
    String quizdown =
        "```\n" +
            "class Equity {\n" +
            "}\n" +
            "```";

    String html = new BlockTransformer().transform(quizdown);

    assertThat(html)
        .isEqualTo("<pre class=\"language-java\">\n" +
                       "class Equity {\n" +
                       "}\n" +
                       "</pre>\n");
  }

  @Test
  public void singleCodeFencedBlockWithBlanksConvertedToSinglePreElementRetainingBlanks() throws Exception {
    String quizdown =
        "```\n" +
            "class Equity {\n" +
            "}\n" +
            "\n" +
            "class Stock extends Equity {\n" +
            "}\n" +
            "```";

    String html = new BlockTransformer().transform(quizdown);

    assertThat(html)
        .isEqualTo("<pre class=\"language-java\">\n" +
                       "class Equity {\n" +
                       "}\n" +
                       "\n" +
                       "class Stock extends Equity {\n" +
                       "}\n" +
                       "</pre>\n");
  }

  @Test
  public void mixedBlocks() throws Exception {
    String quizdown = "Take a look at these two classes:\n" +
        "\n" +
        "```\n" +
        "class Equity {\n" +
        "  public Equity(String name) {\n" +
        "  }\n" +
        "}\n" +
        "\n" +
        "class Stock extends Equity {\n" +
        "}\n" +
        "```\n" +
        "\n" +
        "What's wrong with them?\n";

    // when I parse quizdown blocks
    String html = new BlockTransformer().transform(quizdown);

    // then I expect three "blocks"
    assertThat(html)
        .contains("<p>Take a look at these two classes:</p>\n" +
                      "<pre class=\"language-java\">\n" +
                      "class Equity {\n" +
                      "  public Equity(String name) {\n" +
                      "  }\n" +
                      "}\n" +
                      "\n" +
                      "class Stock extends Equity {\n" +
                      "}\n" +
                      "</pre>\n" +
                      "<p>What's wrong with them?</p>\n");
  }

}
