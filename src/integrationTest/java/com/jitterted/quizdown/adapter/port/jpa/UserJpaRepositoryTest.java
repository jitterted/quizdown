package com.jitterted.quizdown.adapter.port.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@EnableTransactionManagement
public class UserJpaRepositoryTest {

  @Autowired
  private UserJpaRepository userJpaRepository;

  @Test
  @Transactional
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

    System.out.println(foundUser.get());

  }

}