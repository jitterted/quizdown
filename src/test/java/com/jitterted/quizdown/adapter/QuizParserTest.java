package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QuizParserTest {

  @Test
  public void quizdownWithOneFibQuestionParsedIntoListOfOneFibQuestion() throws Exception {
    String fibQuizdown = "|fib|merp| If you wanted to store lots of Customer objects for easy access via their name, what Java Collections class (data structure) would you use?";

    List<Question> questions = new QuizParser().parse(fibQuizdown);

    assertThat(questions)
        .hasSize(1);

    assertThat(questions.get(0).type())
        .isEqualByComparingTo(QuestionType.FIB);
  }

  @Test
  public void withTwoQuestionsHasSizeOfTwo() throws Exception {
    String quizdown = "|mc|A,D| Choose your favorite Java keywords:\n" +
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
        "|fib|goto| What is your least favorite Java keyword?\n";

    List<Question> questions = new QuizParser().parse(quizdown);

    assertThat(questions)
        .hasSize(2)
        .extracting(Question::type)
        .containsExactly(QuestionType.MC, QuestionType.FIB);

  }

}