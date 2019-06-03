package com.jitterted.quizdown.adapter.port.jpa;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.QuestionStore;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.junit.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UserDtoTransformTest {

  @Test
  public void correctlyCreatesDomainUserFromUserDto() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    questionStore.create(QuestionType.FIB, "fib", new DummyAnswerValidator());
    questionStore.create(QuestionType.MC, "mc", new DummyAnswerValidator());
    UserDtoTransformer userDtoTransformer = new UserDtoTransformer(questionStore);

    AnswerDto answerDto1 = new AnswerDto(null, 1, Set.of("c", "d"));
    AnswerDto answerDto2 = new AnswerDto(null, 2, Set.of("hashmap"));
    UserDto userDto = new UserDto(3L, "Ted", Set.of(answerDto1, answerDto2));

    User user = userDtoTransformer.toUser(userDto);

    assertThat(user)
        .isNotNull();

    assertThat(user.getId())
        .isNotNull()
        .isEqualTo(3L);

    assertThat(user.name())
        .isEqualTo(new UserName("Ted"));

    Answer answer1 = new RealAnswer(questionStore.findByNumber(1), "c", "d");
    Answer answer2 = new RealAnswer(questionStore.findByNumber(2), "hashmap");

    assertThat(user.answers())
        .hasSize(2)
        .containsExactlyInAnyOrder(answer1, answer2);
  }

  @Test
  public void correctlyConvertsDomainUserToUserDto() throws Exception {
    QuestionStore questionStore = new QuestionStore();
    questionStore.create(QuestionType.MC, "mc", new DummyAnswerValidator());
    questionStore.create(QuestionType.FIB, "fib", new DummyAnswerValidator());
    UserDtoTransformer userDtoTransformer = new UserDtoTransformer(questionStore);

    User user = new User(new UserName("Calvin"));
    user.setId(7L);
    Answer answer1 = new RealAnswer(questionStore.findByNumber(1), "a", "c");
    Answer answer2 = new RealAnswer(questionStore.findByNumber(2), "map");
    user.answered(answer1);
    user.answered(answer2);

    UserDto userDto = userDtoTransformer.toUserDto(user);

    assertThat(userDto)
        .isNotNull();

    assertThat(userDto.getId())
        .isNotNull()
        .isEqualTo(7L);

    assertThat(userDto.getUserName())
        .isEqualTo("Calvin");

    assertThat(userDto.getAnswers())
        .containsExactly(new AnswerDto(null, 1, Set.of("a", "c")),
                         new AnswerDto(null, 2, Set.of("map")));
  }
}