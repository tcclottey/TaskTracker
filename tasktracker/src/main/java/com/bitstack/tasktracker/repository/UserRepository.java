package com.bitstack.tasktracker.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bitstack.tasktracker.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
