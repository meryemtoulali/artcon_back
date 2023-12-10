package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findUserById(Integer id);
    List<User> findByUsernameContainingIgnoreCase(String query);
    Optional<User> findUserByEmail(String email);
}
