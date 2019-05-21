package com.jitterted.quizdown.adapter.port;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository {

  private final Map<UserName, User> userMap = new HashMap<>();

  @Override
  public User findByName(UserName userName) {
    return userMap.get(userName);
  }

  @Override
  public void save(User user) {
    userMap.put(user.name(), user);
  }
}
