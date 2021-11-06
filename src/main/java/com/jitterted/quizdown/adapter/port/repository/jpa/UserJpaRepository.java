package com.jitterted.quizdown.adapter.port.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserDto, Long> {

    Optional<UserDto> findByUserName(String userName);

}
