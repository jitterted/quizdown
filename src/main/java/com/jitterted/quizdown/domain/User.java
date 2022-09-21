package com.jitterted.quizdown.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class User {
    private final UserName userName;

    private final Map<Integer, QuestionResponse> answers = new HashMap<>();

    // for use by Repository only
    private Long id;

    public User(UserName userName) {
        this.userName = userName;
    }

    public Set<QuestionResponse> answers() {
        return new HashSet<>(answers.values());
    }

    public UserName name() {
        return userName;
    }

    public void answered(QuestionResponse questionResponse) {
        answers.put(questionResponse.questionNumber(), questionResponse);
    }

    public Response responseFor(int questionNumber) {
        QuestionResponse questionResponse = answers.get(questionNumber);
        if (questionResponse == null) {
            return Response.of();
        } else {
            return questionResponse.response();
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
