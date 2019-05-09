package com.jitterted.quizdown;

import org.junit.Test;

import java.util.Map;

public class QuizControllerTest {

  @Test
  public void completedQuizProvidesGradedAnswers() throws Exception {
    QuestionIterator questionIterator = new QuestionIterator("{fib}{\"map\",\"hashmap\"} If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?");
    QuizController quizController = new QuizController(questionIterator);

    // WHEN: we submit correct answer
    quizController.answer(Map.of("q1", "map"));

    // THEN: graded answer is correct
    quizController.quizDone();
  }

}