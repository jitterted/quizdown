package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AnswerService {
  private final QuestionStore questionStore;

  private final Map<String, User> users = new HashMap<>();

  private final Set<Answer> answerSet = new HashSet<>();

  @Autowired
  public AnswerService(QuestionStore questionStore) {
    this.questionStore = questionStore;
  }

  public void process(String user, Map<String, String> answerMap) {
    Map<String, String> stringMap = new HashMap<>(answerMap);

    String questionNumber = stringMap.remove("question");

    String[] response = stringMap.values()
                                 .toArray(new String[0]);

    Question question = questionStore.findByNumber(Integer.parseInt(questionNumber));
    Answer answer = new RealAnswer(question, response);

    answerSet.add(answer);
  }

  public Set<Answer> answers() {
    return answerSet;
  }

  public List<GradedAnswerView> results() {
    return answerSet.stream()
                    .map(GradedAnswerView::from)
                    .sorted(Comparator.comparingInt(GradedAnswerView::getQuestionNumber))
                    .collect(Collectors.toList());
  }

}
