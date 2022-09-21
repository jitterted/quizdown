package com.jitterted.quizdown.adapter.port.repository;

import com.jitterted.quizdown.application.port.UserRepository;
import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FakeUserRepository implements UserRepository {

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
