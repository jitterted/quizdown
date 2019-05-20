package com.jitterted.quizdown.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class User {
  private final String name;

  private final Set<Answer> answers = new HashSet<>();

  public User(String name) {
    this.name = name;
  }

  public Set<Answer> answers() {
    return Collections.unmodifiableSet(answers);
  }

  public String name() {
    return name;
  }

  public void answered(Answer answer) {
    answers.add(answer);
  }
}
