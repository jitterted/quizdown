package com.jitterted.quizdown;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionIteratorTest {


  @Test
  public void quizdownWithOneQuestionHasSizeOfOne() throws Exception {
    String fib = "{fib} If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";
    QuestionIterator questionIterator = new QuestionIterator(fib);

    assertThat(questionIterator)
        .hasNext();
    assertThat(questionIterator.next())
        .isNotBlank();
    assertThat(questionIterator)
        .isExhausted();
  }

  @Test
  public void withTwoQuestionsHasSizeOfTwo() throws Exception {
    String quizdown = "{mc} Choose your favorite Java keywords:\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n" +
        "\n" +
        "C. volatile\n" +
        " \n" +
        "D. switch \n" +
        "\n" +
        "---\n" +
        "\n" +
        "{fib} What is your favorite Java keyword?\n";

    QuestionIterator questionIterator = new QuestionIterator(quizdown);

    questionIterator.next();
    assertThat(questionIterator)
        .hasNext();

    String fib = questionIterator.next();
    assertThat(fib)
        .contains("What is your favorite Java keyword?");

    assertThat(questionIterator)
        .isExhausted();

  }


}