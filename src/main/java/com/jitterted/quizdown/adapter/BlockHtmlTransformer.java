package com.jitterted.quizdown.adapter;

import java.util.Scanner;

public class BlockHtmlTransformer {

    private static final String PRISM_CODE_HTML_TEMPLATE =
            "<pre><code class=\"language-java\">$1</code></pre>\n";
    private final InlineMarkupToHtmlTransformer inlineMarkupToHtmlTransformer = new InlineMarkupToHtmlTransformer();

    // ?s is a flag to turn on DOTALL mode (match across newlines)
    private static final String CODE_FENCE_REGEX = "(?s)```\\n(.*?)```";
    private static final String CODE_FENCE_DELIMITER = "```";

    public String transform(String quizdown) {
        Scanner scanner = new Scanner(quizdown).useDelimiter("\n\n");

        StringBuilder html = new StringBuilder();
        while (scanner.hasNext()) {
            String element = scanner.next();
            if (element.startsWith(CODE_FENCE_DELIMITER)) {
                element = processCodeFencedBlock(scanner, element);
            } else {
                element = transformToParaHtmlElement(element);
            }

            html.append(element);
        }

        return html.toString();
    }

    private String processCodeFencedBlock(Scanner scanner, String element) {
        StringBuilder accumulator = new StringBuilder(element);
        while (scanner.hasNext()) {
            element = scanner.next();
            accumulator.append("\n\n").append(element);
            if (element.endsWith(CODE_FENCE_DELIMITER)) {
                break;
            }
        }
        return accumulator.toString().replaceAll(CODE_FENCE_REGEX, PRISM_CODE_HTML_TEMPLATE);
    }

    private String transformToParaHtmlElement(String element) {
        element = inlineMarkupToHtmlTransformer.toHtml(element.strip());
        return "<p class=\"question\">" + element + "</p>\n";
    }

}
