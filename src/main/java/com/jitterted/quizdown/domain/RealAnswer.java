package com.jitterted.quizdown.domain;

public class RealAnswer implements Answer {
  private final Question question;
  private final Response response;

  public RealAnswer(Question question, String... response) {
    this.question = question;
    this.response = Response.of(response);
  }

  @Override
  public boolean isCorrect() {
    return question.isCorrectFor(response);
  }

  @Override
  public Response response() {
    return response;
  }

  @Override
  public int questionNumber() {
    return question.number();
  }

  @Override
  public Question question() {
    return question;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RealAnswer that = (RealAnswer) o;

    return question.equals(that.question);
  }

  @Override
  public int hashCode() {
    return question.hashCode();
  }

  @Override
  public String toString() {
    return "{Answer: Question = " + question + ", Response = " + response + "}";
  }

}
