package com.jitterted.quizdown.adapter.port.repository.jpa;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
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

  private Answer toAnswer(AnswerDto answerDto) {
    Question question = questionStore.findByNumber(answerDto.getQuestionNumber());
    String[] responses = answerDto.getResponses().toArray(new String[0]);
    return new RealAnswer(question, responses);
  }

  UserDto toUserDto(User user) {
    Set<AnswerDto> answers = user.answers()
                                 .stream()
                                 .map(this::toAnswerDto)
                                 .collect(Collectors.toSet());
    return new UserDto(user.getId(), user.name().getName(), answers);
  }

  private AnswerDto toAnswerDto(Answer answer) {
    return new AnswerDto(null, answer.questionNumber(), answer.response());
  }
}
