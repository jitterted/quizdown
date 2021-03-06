package com.jitterted.quizdown.adapter.port.repository;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserRepositoryMemoryAdapter implements UserRepository {

  private final Map<UserName, User> userMap = new HashMap<>();

  @Override
  public Optional<User> findByName(UserName userName) {
    return Optional.ofNullable(userMap.get(userName));
  }

  @Override
  public void save(User user) {
    userMap.put(user.name(), user);
  }
}
