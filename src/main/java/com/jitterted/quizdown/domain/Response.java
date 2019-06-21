package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;

import java.util.Set;

@EqualsAndHashCode
public class Response {
  private final Set<String> response;

  private Response(Set<String> response) {
    this.response = response;
  }

  public static Response of(String... choices) {
    return new Response(Set.of(choices));
  }

  public boolean matchesAny(Response correctResponse) {
    return correctResponse.response.containsAll(response);
  }

  public boolean allMatch(Response correctResponse) {
    return response.equals(correctResponse.response);
  }

  public Set<String> asSet() {
    return Set.copyOf(response);
  }

}
