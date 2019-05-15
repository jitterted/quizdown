package com.jitterted.quizdown.domain;

import lombok.NonNull;

import java.util.Set;

public interface AnswerValidator {
  boolean isCorrectFor(@NonNull Set<String> response);
}
