package com.jitterted.quizdown.adapter.outbound.jpa;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionResponse;
import com.jitterted.quizdown.domain.RealQuestionResponse;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDtoTransformer {

    private final QuestionStore questionStore;

    @Autowired
    public UserDtoTransformer(QuestionStore questionStore) {
        this.questionStore = questionStore;
    }

    User toUser(UserDto userDto) {
        User user = new User(new UserName(userDto.getUserName()));
        user.setId(userDto.getId());
        userDto.getAnswers()
                .stream()
                .map(this::toAnswer)
                .forEach(user::answered);
        return user;
    }

    private QuestionResponse toAnswer(AnswerDto answerDto) {
        Question question = questionStore.findByNumber(answerDto.getQuestionNumber());
        String[] responses = answerDto.getResponses().toArray(new String[0]);
        return new RealQuestionResponse(question, responses);
    }

    UserDto toUserDto(User user) {
        List<AnswerDto> answers = user.answers()
                                      .stream()
                                      .map(this::toAnswerDto)
                                      .collect(Collectors.toList());
        return new UserDto(user.getId(), user.name().name(), answers);
    }

    private AnswerDto toAnswerDto(QuestionResponse questionResponse) {
        return new AnswerDto(null, questionResponse.questionNumber(), questionResponse.response().asSet());
    }
}
