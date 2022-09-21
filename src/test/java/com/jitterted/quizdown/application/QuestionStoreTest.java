package com.jitterted.quizdown.application;

import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class QuestionStoreTest {

    @Test
    public void noQuestionIsFoundForUnknownNumber() throws Exception {
        QuestionStore questionStore = new QuestionStore();

        assertThat(questionStore.findByNumber(1))
                .isNull();
    }

    @Test
    public void newQuestionHasQuestionNumberOfOne() throws Exception {
        QuestionStore questionStore = new QuestionStore();

        Question question = questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());

        assertThat(question.number())
                .isEqualTo(1);
    }

    @Test
    public void createdQuestionIsSavedInStore() throws Exception {
        QuestionStore questionStore = new QuestionStore();

        Question question = questionStore.create(QuestionType.MC, "pick one", new DummyAnswerValidator());

        assertThat(questionStore.findByNumber(question.number()))
                .isEqualTo(question);
    }

}