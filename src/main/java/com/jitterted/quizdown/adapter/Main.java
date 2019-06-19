package com.jitterted.quizdown.adapter;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Main {
  public static void main(String[] args) {
    String text = "Block one.\n" +
        "Still part of block one.\n" +
        "Yep, still part of block one.\n" +
        "\n" +
        "Block two here.\n" +
        "Still part of block two.\n" +
        "\n" +
        "Last block here.\n";
    Scanner scanner = new Scanner(text).useDelimiter("\n\n");
    List<String> paragraphs = scanner.tokens()
                                     .map(s -> "<p>" + s + "</p>\n")
                                     .collect(Collectors.toList());

    paragraphs.forEach(System.out::println);
  }
}