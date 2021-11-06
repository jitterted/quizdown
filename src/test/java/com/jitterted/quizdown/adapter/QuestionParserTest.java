package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionParserTest {

  @Test
  public void fibQuestionParsedWithAnswerValidator() throws Exception {
    String quizdown = "|fib|map,hashmap| If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?";

    Question question = new QuestionParser().parse(quizdown);

    Question expectedQuestion = new Question(
        QuestionType.FIB,
        "If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?",
        DefaultAnswerValidator.forType(QuestionType.FIB).correctChoices("map", "hashmap"));

    assertThat(question)
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(expectedQuestion);
  }

  @Test
  public void mcParsedCorrectlyIntoQuestion() throws Exception {
    String mc = "|mc|A,D| Choose your favorite Java keywords:\n" +
        "\n" +
        "===\n" +
        "\n" +
        "A. final\n" +
        "\n" +
        "B. var\n" +
        "\n" +
        "C. volatile\n" +
        " \n" +
        "D. switch \n";

    Question question = new QuestionParser().parse(mc);

    Question expectedQuestion = new Question(
        QuestionType.MC,
        "Choose your favorite Java keywords:\n" +
            "\n" +
            "===\n" +
            "\n" +
            "A. final\n" +
            "\n" +
            "B. var\n" +
            "\n" +
            "C. volatile\n" +
            " \n" +
            "D. switch \n",
        DefaultAnswerValidator.forType(QuestionType.MC).correctChoices("a", "d"));

    assertThat(question)
            .usingRecursiveComparison()
            .ignoringFields("number")
            .isEqualTo(expectedQuestion);
  }

}