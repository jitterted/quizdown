package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerService {
  private final QuestionStore questionStore;

  private final Set<Answer> answerSet = new HashSet<>();

  @Autowired
  public AnswerService(QuestionStore questionStore) {
    this.questionStore = questionStore;
  }

  public void process(Map<String, String> map) {
    String questionNumber = map.get("question");
    Question question = questionStore.findByNumber(Integer.parseInt(questionNumber));

    String[] response = map.entrySet()
                           .stream()
                           .filter(entry -> !entry.getKey().equals("question"))
                           .map(Map.Entry::getValue)
                           .toArray(String[]::new);

    Answer answer = new Answer(question, response);

    answerSet.add(answer);
  }

  public Set<Answer> answers() {
    return answerSet;
  }

  public List<GradedAnswerView> results() {
    return answerSet.stream()
                    .map(GradedAnswerView::from)
                    .collect(Collectors.toList());
  }

}
