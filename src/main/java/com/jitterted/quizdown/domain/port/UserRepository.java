package com.jitterted.quizdown.domain.port;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;

public interface UserRepository {
  User findByName(UserName userName);

  void save(User user);
}
