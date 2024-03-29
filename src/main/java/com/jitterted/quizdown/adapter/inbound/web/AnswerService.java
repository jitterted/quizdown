package com.jitterted.quizdown.adapter.inbound.web;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.application.port.QuizCompletedNotifier;
import com.jitterted.quizdown.application.port.UserRepository;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionResponse;
import com.jitterted.quizdown.domain.RealQuestionResponse;
import com.jitterted.quizdown.domain.Response;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class AnswerService {
    private final QuestionStore questionStore;
    private final UserRepository userRepository;
    private final QuizCompletedNotifier quizCompleteNotifier;

    @Autowired
    public AnswerService(QuestionStore questionStore,
                         UserRepository userRepository,
                         QuizCompletedNotifier quizCompleteNotifier) {
        this.questionStore = questionStore;
        this.userRepository = userRepository;
        this.quizCompleteNotifier = quizCompleteNotifier;
    }

    public void processAnswer(String userNameString, Map<String, String> answerMap) {
        UserName userName = new UserName(userNameString);
        User user = userRepository.findByName(userName)
                .orElse(new User(userName));

        Map<String, String> stringMap = new HashMap<>(answerMap);
        String questionNumber = stringMap.remove("question");

        Question question = questionStore.findByNumber(Integer.parseInt(questionNumber));
        QuestionResponse questionResponse = new RealQuestionResponse(question, convertMapToResponse(stringMap));

        user.answered(questionResponse);
        userRepository.save(user);
    }

    String[] convertMapToResponse(Map<String, String> stringMap) {
        return stringMap.values()
                .stream()
                .map(String::toLowerCase)
                .toArray(String[]::new);
    }

    public Response responseFor(String name, int questionNumber) {
        UserName userName = new UserName(name);
        return userRepository.findByName(userName)
                .map(user -> user.responseFor(questionNumber))
                .orElse(Response.of());
    }

    public Set<QuestionResponse> answersFor(String name) {
        UserName userName = new UserName(name);
        return userRepository.findByName(userName)
                .map(User::answers)
                .orElse(Collections.emptySet());
    }

    public void quizCompletedFor(String name) {
        UserName userName = new UserName(name);
        User user = userRepository.findByName(userName)
                .orElseThrow();
        quizCompleteNotifier.quizCompleted(user);
    }
}
