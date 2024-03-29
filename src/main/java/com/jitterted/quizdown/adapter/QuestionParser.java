package com.jitterted.quizdown.adapter;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.domain.DefaultAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;

import java.util.Scanner;

public class QuestionParser {
    private QuestionStore questionStore = new QuestionStore();

    public Question parse(String quizdown) {
        Scanner scanner = scannerFor(quizdown);

        QuestionType questionType = questionTypeFrom(scanner);

        DefaultAnswerValidator answerValidator = answerValidatorFrom(scanner, questionType);

        String content = removeMetaTags(quizdown);

        Question question = questionStore.create(questionType, content, answerValidator);

        return question;
    }

    public String removeMetaTags(String quizdown) {
        return quizdown.substring(quizdown.indexOf("| ") + 2);
    }

    private QuestionType questionTypeFrom(Scanner scanner) {
        String questionType = scanner.next().strip().toUpperCase();
        return QuestionType.valueOf(questionType);
    }

    private DefaultAnswerValidator answerValidatorFrom(Scanner scanner, QuestionType questionType) {
        String correctChoiceString = scanner.next().strip().toLowerCase();
        String[] correctChoices = correctChoiceString.split(",");
        return DefaultAnswerValidator.forType(questionType)
                .correctChoices(correctChoices);
    }

    private Scanner scannerFor(String quizdown) {
        return new Scanner(quizdown).useDelimiter("\\|");
    }

}
