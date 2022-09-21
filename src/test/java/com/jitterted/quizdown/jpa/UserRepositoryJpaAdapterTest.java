package com.jitterted.quizdown.jpa;

import com.jitterted.quizdown.adapter.outbound.jpa.UserJpaRepository;
import com.jitterted.quizdown.application.port.UserRepository;
import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
@ActiveProfiles("test")
@Tag("integration")
public class UserRepositoryJpaAdapterTest {

    @Autowired
    UserJpaRepository userJpaRepository;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void clear() {
        userJpaRepository.deleteAll();
    }

    @Test
    public void newlySavedUserHasId() throws Exception {
        UserName thanosName = new UserName("Thanos");
        User thanos = new User(thanosName);

        userRepository.save(thanos);

        Optional<User> found = userRepository.findByName(thanosName);

        assertThat(found)
                .isPresent();

        assertThat(found.get().getId())
                .isNotNull();
    }

    @Test
    public void previouslySavedUserIsUpdatedInDatabaseAfterSave() throws Exception {
        UserName thanosName = new UserName("Thanos");
        User thanos = new User(thanosName);

        userRepository.save(thanos);

        User found = userRepository.findByName(thanosName).get();

        Question question = new Question(QuestionType.FIB, "pick one", new DummyAnswerValidator(), 1);
        Answer answer = new RealAnswer(question, "response");
        found.answered(answer);

        userRepository.save(found);

        User foundAgain = userRepository.findByName(thanosName).get();

        assertThat(foundAgain.answers())
                .hasSize(1);
    }

}