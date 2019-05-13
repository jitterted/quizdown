package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.domain.AnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;

import java.util.Scanner;

public class QuestionParser {
  private QuestionStore questionStore = new QuestionStore();

  public Question parse(String quizdown) {
    Scanner scanner = scannerFor(quizdown);

    QuestionType type = questionTypeFrom(scanner);

    AnswerValidator answerValidator = answerValidatorFrom(scanner);

    String content = quizdown.substring(quizdown.indexOf("| ") + 2);

    Question question = questionStore.create(type, content, answerValidator);

    return question;
  }

  private QuestionType questionTypeFrom(Scanner scanner) {
    String questionType = scanner.next().strip().toUpperCase();
    return QuestionType.valueOf(questionType);
  }

  private AnswerValidator answerValidatorFrom(Scanner scanner) {
    String correctChoiceString = scanner.next().strip();
    String[] correctChoices = correctChoiceString.split(",");
    return new AnswerValidator(correctChoices);
  }

  private Scanner scannerFor(String quizdown) {
    return new Scanner(quizdown).useDelimiter("\\|");
  }

}
