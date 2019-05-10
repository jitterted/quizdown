package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class AnswerService {
  private final Set<Answer> answerSet = new HashSet<>();

  public void process(Map<String, String> map) {
    String questionNumber = map.get("question");
    String response = map.keySet()
                         .stream()
                         .filter(s -> !s.equals("question"))
                         .findFirst()
                         .get();

    Answer answer = new Answer(null, response);

    answerSet.add(answer);
  }

  public Set<Answer> answers() {
    return answerSet;
  }
}
