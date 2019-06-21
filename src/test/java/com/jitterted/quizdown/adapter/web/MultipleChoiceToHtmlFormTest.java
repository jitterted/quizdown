package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.adapter.web.MultipleChoiceTransformer.Choice;
import org.junit.Ignore;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

@Ignore("Use snapshot library instead")
public class MultipleChoiceToHtmlFormTest {

  @Test
  public void convertsIndividualChoiceToHtml() throws Exception {
    String choice = "A. First choice";

    String html = new Choice().toHtml(choice);

    assertThat(html)
        .isEqualTo(
            "<div class=\"field\">\n" +
                "  <div class=\"control\">\n" +
                "    <label class=\"checkbox\">\n" +
                "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                "      First choice\n" +
                "    </label>\n" +
                "  </div>\n" +
                "</div>"
        );
  }

  @Test
  public void convertSecondChoiceToHtmlWithChoiceIdOf2() throws Exception {
    Choice choice = new Choice();
    choice.toHtml("one"); // skip to choice 2

    String html = choice.toHtml("B. Second choice");

    assertThat(html)
        .isEqualTo("<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "      Second choice\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>"
        );
  }

  @Test
  public void questionWithSingleLineContentAndTwoChoicesConvertedToHtmlWithParaAndForm() throws Exception {
    String mc = "Choose your favorite Java keywords:\n" +
        "\n" +
        "===\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n";

    String html = new MultipleChoiceTransformer().toHtml(mc);

    assertThat(html)
        .isEqualTo("<p>Choose your favorite Java keywords:</p>\n" +
                       "\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                       "      final\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "      var\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n"
        );
  }

  @Test
  public void questionInlineMarkupConvertedToHtmlWithEntitiesEscaped() throws Exception {
    String questionText = "Take a look at _these_ two List<String>:\n" +
        "\n" +
        "```\n" +
        "class Enum<Equity> {\n" +
        "  boolean b = true && false;\n" +
        "}\n" +
        "\n" +
        "class Stock extends Equity {\n" +
        "}\n" +
        "```\n" +
        "\n" +
        "What's wrong with this & that?\n" +
        "\n" +
        "===\n" +
        "\n" +
        "A. Needs `Stock<>` instance\n" +
        "\n" +
        "B. It's all **wrong**\n";

    String html = new MultipleChoiceTransformer().toHtml(questionText);

    assertThat(html)
        .isEqualTo("<p>Take a look at <em>these</em> two List&lt;String&gt;:</p>\n" +
                       "<pre><code class=\"language-java\">" +
                       "class Enum&lt;Equity&gt; {\n" +
                       "  boolean b = true &amp;&amp; false;\n" +
                       "}\n" +
                       "\n" +
                       "class Stock extends Equity {\n" +
                       "}\n" +
                       "</code></pre>\n" +
                       "<p>What&#39;s wrong with this &amp; that?</p>\n" +
                       "\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n" +
                       "      Needs <code style=\"background: none !important\" class=\"language-java\">Stock&lt;&gt;</code> instance\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n" +
                       "<div class=\"field\">\n" +
                       "  <div class=\"control\">\n" +
                       "    <label class=\"checkbox\">\n" +
                       "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n" +
                       "      It&#39;s all <strong>wrong</strong>\n" +
                       "    </label>\n" +
                       "  </div>\n" +
                       "</div>\n");
  }

}
