package com.jitterted.quizdown.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionResponseTest {

    @Test
    public void correctFillInBlankAnswerMarkedAsCorrect() throws Exception {
        DefaultAnswerValidator validator = DefaultAnswerValidator
                .forType(QuestionType.FIB)
                .correctChoices("map", "hashmap");
        Question fibQuestion = new Question(QuestionType.FIB, "", validator);
        QuestionResponse questionResponse = new RealQuestionResponse(fibQuestion, "map");

        assertThat(questionResponse.isCorrect())
                .isTrue();
    }

    @Test
    public void incorrectFillInBlankAnswerMarkedAsNotCorrect() throws Exception {
        DefaultAnswerValidator validator = DefaultAnswerValidator
                .forType(QuestionType.FIB)
                .correctChoices("map", "hashmap");
        Question fibQuestion = new Question(QuestionType.FIB, "", validator);
        QuestionResponse questionResponse = new RealQuestionResponse(fibQuestion, "wrong");

        assertThat(questionResponse.isCorrect())
                .isFalse();
    }

    @Test
    public void correctMultipleChoiceAnswerMarkedAsCorrect() throws Exception {
        DefaultAnswerValidator mcValidator = DefaultAnswerValidator.forType(QuestionType.MC)
                .correctChoices("a", "b");

        Question question = new Question(QuestionType.MC, "choose wisely", mcValidator);

        QuestionResponse questionResponse = new RealQuestionResponse(question, "a", "b");

        assertThat(questionResponse.isCorrect())
                .isTrue();
    }

    @Test
    public void incorrectMultipleChoiceAnswerMarkedAsNotCorrect() throws Exception {
        DefaultAnswerValidator mcValidator = DefaultAnswerValidator.forType(QuestionType.MC)
                .correctChoices("a", "b");

        Question question = new Question(QuestionType.MC, "choose poorly", mcValidator);

        QuestionResponse questionResponse = new RealQuestionResponse(question, "b");

        assertThat(questionResponse.isCorrect())
                .isFalse();
    }
}
