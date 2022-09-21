package com.jitterted.quizdown.adapter.inbound.web;

import com.jitterted.quizdown.adapter.inbound.web.MultipleChoiceTransformer.Choice;
import com.jitterted.quizdown.domain.Response;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MultipleChoiceToHtmlFormTest {

    @Test
    public void convertsUnselectedIndividualChoiceToHtml() throws Exception {
        String choice = "A. First choice";

        String html = new Choice().toHtml(choice, Response.of());

        assertThat(html)
                .contains(
                        "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n",
                        "      First choice\n"
                );
    }

    @Test
    public void convertsSelectedChoiceToHtmlWithCheckedAttribute() throws Exception {
        String choice = "A. Best answer";

        String html = new Choice().toHtml(choice, Response.of("a"));

        assertThat(html)
                .contains("value=\"a\" checked/>");
    }

    @Test
    public void convertSecondChoiceToHtmlWithChoiceIdOf2() throws Exception {
        Choice choice = new Choice();
        choice.toHtml("one", Response.of()); // skip to choice 2

        String html = choice.toHtml("B. Second choice", Response.of());

        assertThat(html)
                .contains(
                        "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n",
                        "      Second choice\n"
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

        String html = new MultipleChoiceTransformer().toHtml(mc, Response.of());

        assertThat(html)
                .contains("<p class=\"question\">Choose your favorite Java keywords:</p>\n",
                          "      <input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n",
                          "      final\n",
                          "      <input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\"/>\n",
                          "      var\n")
                .doesNotContain("===");
    }

    @Test
    public void questionWithSelectedChoiceIsChecked() throws Exception {
        String mc = "Choose your favorite Java keywords:\n" +
                "\n" +
                "===\n" +
                "\n" +
                "A. final\n" +
                "\n" +
                "B. var\n";

        String html = new MultipleChoiceTransformer().toHtml(mc, Response.of("b"));

        assertThat(html)
                .contains("<input type=\"checkbox\" id=\"q1ch1\" name=\"q1ch1\" value=\"a\"/>\n",
                          "<input type=\"checkbox\" id=\"q1ch2\" name=\"q1ch2\" value=\"b\" checked/>\n"
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

        String html = new MultipleChoiceTransformer().toHtml(questionText, Response.of());

        assertThat(html)
                .contains("<p class=\"question\">Take a look at <em>these</em> two List&lt;String&gt;:</p>\n",
                          "<pre><code class=\"language-java\">",
                          "class Enum&lt;Equity&gt; {\n" +
                                  "  boolean b = true &amp;&amp; false;\n" +
                                  "}\n" +
                                  "\n" +
                                  "class Stock extends Equity {\n" +
                                  "}\n" +
                                  "</code></pre>\n",
                          "<p class=\"question\">What&#39;s wrong with this &amp; that?</p>\n",
                          "      Needs <code style=\"background: none !important\" class=\"language-java\">Stock&lt;&gt;</code> instance\n",
                          "      It&#39;s all <strong>wrong</strong>\n"
                );
    }

}
