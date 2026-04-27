package com.cg.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email (useful for login)
    Optional<User> findByEmail(String email);

    // Find user by username
    Optional<User> findByUsername(String username);

    // Search users by username (partial match)
    List<User> findByUsernameContaining(String keyword);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if username already exists
    boolean existsByUsername(String username);
}