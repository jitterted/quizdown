package com.jitterted.quizdown.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
  private final UserName userName;

  private final Map<Integer, Answer> answers = new HashMap<>();

  // for use by Repository only
  @Getter
  @Setter
  private Long id;

  public User(UserName userName) {
    this.userName = userName;
  }

  public Set<Answer> answers() {
    return new HashSet<>(answers.values());
  }

  public UserName name() {
    return userName;
  }

  public void answered(Answer answer) {
    answers.put(answer.questionNumber(), answer);
  }

  public Response responseFor(int questionNumber) {
    Answer answer = answers.get(questionNumber);
    if (answer == null) {
      return Response.of();
    } else {
      return answer.response();
    }
  }
}
