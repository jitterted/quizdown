package com.jitterted.quizdown.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {
  private final UserName userName;

  private final Set<Answer> answers = new HashSet<>();

  // for use by Repository only
  @Getter
  @Setter
  private Long id;

  public User(UserName userName) {
    this.userName = userName;
  }

  public Set<Answer> answers() {
    return Collections.unmodifiableSet(answers);
  }

  public UserName name() {
    return userName;
  }

  public void answered(Answer answer) {
    answers.add(answer);
  }
}
