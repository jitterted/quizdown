package com.jitterted.quizdown.adapter.port.repository.jpa;

import com.jitterted.quizdown.domain.User;
import com.jitterted.quizdown.domain.UserName;
import com.jitterted.quizdown.domain.port.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpaAdapter implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserDtoTransformer userDtoTransformer;

    @Autowired
    public UserRepositoryJpaAdapter(UserJpaRepository jpaRepository,
                                    UserDtoTransformer userDtoTransformer) {
        this.jpaRepository = jpaRepository;
        this.userDtoTransformer = userDtoTransformer;
    }

    @Override
    public Optional<User> findByName(UserName userName) {
        Optional<UserDto> userDto = jpaRepository.findByUserName(userName.name());
        return userDto.map(userDtoTransformer::toUser);
    }

    @Override
    public void save(User user) {
        UserDto userDto = userDtoTransformer.toUserDto(user);
        jpaRepository.save(userDto);
    }
}
