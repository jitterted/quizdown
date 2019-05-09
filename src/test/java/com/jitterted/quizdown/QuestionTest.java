package com.jitterted.quizdown;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionTest {

  @Test
  public void fibQuestionParsedWithAnswerValidator() throws Exception {
    String quizdown = "{fib}{\"map\",\"hashmap\"} If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?";

    // do stuff
    Question question = new QuestionParser().parse(quizdown);

    Question expectedQuestion = new Question(QuestionType.FIB, "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?", new AnswerValidator("map", "hashmap"));

    assertThat(question)
        .isEqualTo(expectedQuestion);
  }
}