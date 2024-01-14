package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @BeforeEach
    void setUp() {
        // Add test data using builders or other setup
        userRepository.save(User.builder()
                        .id(1)
                .username("user1")
                .email("user1@example.com")
                .firstname("John")
                .lastname("Doe")
                .title("Developer")
                .type("Employee")
                .location("City1")
                .build());

        userRepository.save(User.builder()
                        .id(2)
                .username("user2")
                .email("user2@example.com")
                .firstname("Jane")
                .lastname("Smith")
                .title("Designer")
                .type("Contractor")
                .location("City2")
                .build());
    }

    @Test
    void testFindByUsername_Success() {
        Optional<User> userOptional = userRepository.findByUsername("user1");

        assertTrue(userOptional.isPresent());
        assertEquals("user1", userOptional.get().getUsername());
    }

    @Test
    void testFindByUsername_NotFound() {
        Optional<User> userOptional = userRepository.findByUsername("nonexistent");

        assertTrue(userOptional.isEmpty());
    }

    @Test
    void testFindUserById_Success() {
        Optional<User> savedUserOptional = userRepository.findByUsername("user1");

        assertTrue(savedUserOptional.isPresent());

        Integer userId = savedUserOptional.get().getId();

        Optional<User> userOptional = userRepository.findUserById(userId);

        assertTrue(userOptional.isPresent());
        assertEquals("user1", userOptional.get().getUsername());
    }


    @Test
    void testFindUserById_NotFound() {
        Optional<User> userOptional = userRepository.findUserById(100);

        assertTrue(userOptional.isEmpty());
    }

    @Test
    void testFindAll_Success() {
        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void testSearchUser_Success() {
        List<User> result = userRepository.searchUser("John", "Developer", "Employee", "City1");

        assertEquals(1,result.size());
        assertEquals("user1", result.get(0).getUsername());
    }

    @Test
    void testSearchUser_NoResults() {
        List<User> result = userRepository.searchUser("Nonexistent", "Developer", "Employee", "City1");

        assertTrue(result.isEmpty());
    }
}