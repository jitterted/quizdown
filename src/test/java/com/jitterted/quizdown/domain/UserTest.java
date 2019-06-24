package com.jitterted.quizdown.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

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

    Answer dummyAnswer = new DummyAnswer();
    user.answered(dummyAnswer);

    assertThat(user.answers())
        .containsOnly(dummyAnswer);
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

}