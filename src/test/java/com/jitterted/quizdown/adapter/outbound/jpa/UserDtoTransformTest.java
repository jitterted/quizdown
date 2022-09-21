package com.jitterted.quizdown.adapter.outbound.jpa;

import com.jitterted.quizdown.application.QuestionStore;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.QuestionResponse;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealQuestionResponse;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class UserDtoTransformTest {

    @Test
    public void correctlyCreatesDomainUserFromUserDto() throws Exception {
        QuestionStore questionStore = new QuestionStore();
        questionStore.create(QuestionType.FIB, "fib", new DummyAnswerValidator());
        questionStore.create(QuestionType.MC, "mc", new DummyAnswerValidator());
        UserDtoTransformer userDtoTransformer = new UserDtoTransformer(questionStore);

        AnswerDto answerDto1 = new AnswerDto(null, 1, Set.of("c", "d"));
        AnswerDto answerDto2 = new AnswerDto(null, 2, Set.of("hashmap"));
        UserDto userDto = new UserDto(3L, "Ted", List.of(answerDto1, answerDto2));

        User user = userDtoTransformer.toUser(userDto);

        assertThat(user)
                .isNotNull();

        assertThat(user.getId())
                .isNotNull()
                .isEqualTo(3L);

        assertThat(user.name())
                .isEqualTo(new UserName("Ted"));

        QuestionResponse questionResponse1 = new RealQuestionResponse(questionStore.findByNumber(1), "c", "d");
        QuestionResponse questionResponse2 = new RealQuestionResponse(questionStore.findByNumber(2), "hashmap");

        assertThat(user.answers())
                .hasSize(2)
                .containsExactlyInAnyOrder(questionResponse1, questionResponse2);
    }

    @Test
    public void correctlyConvertsDomainUserToUserDto() throws Exception {
        QuestionStore questionStore = new QuestionStore();
        questionStore.create(QuestionType.MC, "mc", new DummyAnswerValidator());
        questionStore.create(QuestionType.FIB, "fib", new DummyAnswerValidator());
        UserDtoTransformer userDtoTransformer = new UserDtoTransformer(questionStore);

        User user = new User(new UserName("Calvin"));
        user.setId(7L);
        QuestionResponse questionResponse1 = new RealQuestionResponse(questionStore.findByNumber(1), "a", "c");
        QuestionResponse questionResponse2 = new RealQuestionResponse(questionStore.findByNumber(2), "map");
        user.answered(questionResponse1);
        user.answered(questionResponse2);

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