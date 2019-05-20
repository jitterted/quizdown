package com.jitterted.quizdown.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {

  @Test
  public void usersMustHaveName() throws Exception {
    User user = new User("Jim");

    assertThat(user.name())
        .isEqualTo("Jim");
  }

  @Test
  public void newUserHasNoAnswers() throws Exception {
    User user = new User("Who");

    assertThat(user.answers())
        .isEmpty();
  }

  @Test
  public void newUserWithAnswerAddedHasOneAnswer() throws Exception {
    User user = new User("One");

    Answer dummyAnswer = new DummyAnswer();
    user.answered(dummyAnswer);

    assertThat(user.answers())
        .containsOnly(dummyAnswer);
  }

}