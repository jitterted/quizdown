package com.jitterted.quizdown.adapter.port.repository.jpa;

import com.jitterted.quizdown.domain.Answer;
import com.jitterted.quizdown.domain.DummyAnswerValidator;
import com.jitterted.quizdown.domain.Question;
import com.jitterted.quizdown.domain.QuestionType;
import com.jitterted.quizdown.domain.RealAnswer;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class UserRepositoryJpaAdapterTest {

  @Autowired
  UserJpaRepository userJpaRepository;

  @Autowired
  UserRepository userRepository;

  @Before
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