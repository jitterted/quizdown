package com.jitterted.quizdown.adapter;

import java.util.Scanner;

public class BlockTransformer {

  // ?s is a flag to turn on DOTALL mode (match across newlines)
  public static final String CODE_FENCE_REGEX = "(?s)```\\n(.*?)```";
  public static final String CODE_FENCE_DELIMITER = "```";

  public String transform(String quizdown) {
    Scanner scanner = new Scanner(quizdown).useDelimiter("\n\n");

    StringBuilder html = new StringBuilder();
    while (scanner.hasNext()) {
      String element = scanner.next();
      if (element.startsWith(CODE_FENCE_DELIMITER)) {
        StringBuilder accumulator = new StringBuilder(element);
        while (scanner.hasNext()) {
          element = scanner.next();
          accumulator.append("\n\n").append(element);
          if (element.endsWith(CODE_FENCE_DELIMITER)) {
            break;
          }
        }
        element = accumulator.toString().replaceAll(CODE_FENCE_REGEX, "<pre class=\"language-java\">\n$1</pre>\n");
      } else {
        element = "<p>" + element.strip() + "</p>\n";
      }

      html.append(element);
    }

    return html.toString();

//    return scanner.tokens()
//                  .map(this::surroundWithParagraphElementIfNotCodeFenced)
//                  .map(s -> s.replaceAll(CODE_FENCE_REGEX, "<pre class=\"language-java\">\n$1</pre>\n"))
//                  .collect(Collectors.toList());
  }

}
