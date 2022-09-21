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

        Answer stubAnswer = new StubAnswer(false, Response.of(), 13);
        user.answered(stubAnswer);

        assertThat(user.answers())
                .containsOnly(stubAnswer);
    }

    @Test
    public void userWithNoAnswerForQuestionReturnsEmptyResponse() throws Exception {
        User user = new User(new UserName("No Answer"));

        assertThat(user.responseFor(1).asSet())
                .isEmpty();
    }

    @Test
    public void userWithPreviousAnswerForQuestionReturnsItAsResponse() throws Exception {
        User user = new User(new UserName("One answer"));
        Answer stubAnswer = new StubAnswer(false, Response.of("x"), 13);
        user.answered(stubAnswer);

        assertThat(user.responseFor(13).asSet())
                .containsOnly("x");
    }

    @Test
    public void userOverwritesPriorAnswerWithNewAnswer() throws Exception {
        var questionStore = new QuestionStore();
        Question question = questionStore.create(QuestionType.MC, "Choose A, B, or C?", new DummyAnswerValidator());

        User user = new User(new UserName("Hobbes"));

        Answer first = new RealAnswer(question, "a");
        user.answered(first);

        Answer second = new RealAnswer(question, "c");
        user.answered(second);

        assertThat(user.answers())
                .hasSize(1);

        assertThat(user.responseFor(1).asSet())
                .containsOnly("c");
    }

}