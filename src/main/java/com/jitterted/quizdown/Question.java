package com.jitterted.quizdown;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class Question {
  @NonNull
  private final QuestionType type; //: Enum? {fib}
  //AnswerValidator (some "matcher"-like thing) {"map","hashmap"}
  //content: If you wanted to store lots of Customer objects for easy access via their name, what Java Collections type (data structure) would you use?
  @NonNull
  private final String content;
  @NonNull
  private final AnswerValidator answer;

  public boolean isCorrectFor(String response) {
    return answer.isCorrectFor(response);
  }

  @Override
  public String toString() {
    return type.toString() + ": " + content + " -> " + answer;
  }
}
