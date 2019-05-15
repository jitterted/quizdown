package com.jitterted.quizdown.domain;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.Set;

@EqualsAndHashCode
public class DummyAnswerValidator implements AnswerValidator {
  @Override
  public boolean isCorrectFor(@NonNull Set<String> response) {
    throw new UnsupportedOperationException("This is a dummy.");
  }

}
