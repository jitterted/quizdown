package com.jitterted.quizdown.domain;

import com.jitterted.quizdown.application.QuestionStore;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class UserTest {

    @Test
    public void usersMustHaveName() throws Exception {
        User user = new User(new UserName("Jim"));

        assertThat(user.name())
                .isEqualTo(new UserName("Jim"));
    }

    @Test
    public void newUserHasNoAnswers() throws Exception {
        User user = new User(new UserName("Who"));

        assertThat(user.answers())
                .isEmpty();
    }

    @Test
    public void newUserWithAnswerAddedHasOneAnswer() throws Exception {
        User user = new User(new UserName("One"));

        QuestionResponse stubQuestionResponse = new StubQuestionResponse(false, Response.of(), 13);
        user.answered(stubQuestionResponse);

        assertThat(user.answers())
                .containsOnly(stubQuestionResponse);
    }

    @Test
    public void userWithNoAnswerForQuestionReturnsEmptyResponse() throws Exception {
        User user = new User(new UserName("No QuestionResponse"));

        assertThat(user.responseFor(1).asSet())
                .isEmpty();
    }

    @Test
    public void userWithPreviousAnswerForQuestionReturnsItAsResponse() throws Exception {
        User user = new User(new UserName("One answer"));
        QuestionResponse stubQuestionResponse = new StubQuestionResponse(false, Response.of("x"), 13);
        user.answered(stubQuestionResponse);

        assertThat(user.responseFor(13).asSet())
                .containsOnly("x");
    }

    @Test
    public void userOverwritesPriorAnswerWithNewAnswer() throws Exception {
        var questionStore = new QuestionStore();
        Question question = questionStore.create(QuestionType.MC, "Choose A, B, or C?", new DummyAnswerValidator());

        User user = new User(new UserName("Hobbes"));

        QuestionResponse first = new RealQuestionResponse(question, "a");
        user.answered(first);

        QuestionResponse second = new RealQuestionResponse(question, "c");
        user.answered(second);

        assertThat(user.answers())
                .hasSize(1);

        assertThat(user.responseFor(1).asSet())
                .containsOnly("c");
    }

}