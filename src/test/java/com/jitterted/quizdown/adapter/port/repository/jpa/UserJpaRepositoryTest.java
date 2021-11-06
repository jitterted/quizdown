package com.jitterted.quizdown.adapter.port.repository.jpa;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@Tag("integration")
@DataJpaTest
public class UserJpaRepositoryTest {

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    public void storeAndRetrieveUsersWithAnswers() throws Exception {
        AnswerDto answer1 = new AnswerDto(null, 1, Set.of("A", "B"));
        AnswerDto answer2 = new AnswerDto(null, 2, Set.of("A", "B"));
        UserDto calvin = new UserDto(null, "Calvin", Set.of(answer1, answer2));

        UserDto savedCalvin = userJpaRepository.save(calvin);
        assertThat(savedCalvin.getId())
                .isNotNull()
                .isGreaterThanOrEqualTo(0);

        Optional<UserDto> foundUser = userJpaRepository.findByUserName("Calvin");
        assertThat(foundUser)
                .isPresent();

    }

}