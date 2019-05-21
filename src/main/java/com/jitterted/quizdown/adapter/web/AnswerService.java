package com.jitterted.quizdown.adapter.web;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AnswerService {
  private final QuestionStore questionStore;
  private final UserRepository userRepository;

  @Autowired
  public AnswerService(QuestionStore questionStore, UserRepository userRepository) {
    this.questionStore = questionStore;
    this.userRepository = userRepository;
  }

  public void process(String userName, Map<String, String> answerMap) {
    User user = userRepository.findByName(new UserName(userName));

    Map<String, String> stringMap = new HashMap<>(answerMap);
    String questionNumber = stringMap.remove("question");

    String[] response = stringMap.values()
                                 .toArray(new String[0]);

    Question question = questionStore.findByNumber(Integer.parseInt(questionNumber));
    Answer answer = new RealAnswer(question, response);

    user.answered(answer);
  }

  public Set<Answer> answersFor(String name) {
    User user = userRepository.findByName(new UserName(name));
    return user.answers();
  }

}
