package com.jitterted.quizdown.application.port;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(UserName userName);

    void save(User user);
}
