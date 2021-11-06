package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Response;

public class FillInTheBlankTransformer implements HtmlTransformer {

    @Override
    public String toHtml(String content, Response response) {
        String template =
                "  <p class=\"question\">%1$s</p>\n" +
                        "  <div class=\"field\">\n" +
                        "    <div class=\"control\">\n" +
                        "      <input class=\"input\" type=\"text\" id=\"q\" name=\"q\" size=\"20\" value=\"%2$s\">\n" +
                        "    </div>\n" +
                        "  </div>\n";

        String value = String.join("", response.asSet());
        return String.format(template, content, value);
    }
}
