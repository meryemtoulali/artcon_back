package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

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
                .build());

        userRepository.save(User.builder()
                        .id(2)
                .username("user2")
                .email("user2@example.com")
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

//    @Test
//    void testFindUserById_Success() {
//        User user = userRepository.findUserById(1).orElseThrow();
//
//        assertNotNull(user);
//        assertEquals(1, user.getId());
//    }

    @Test
    void testFindUserById_NotFound() {
        Optional<User> userOptional = userRepository.findUserById(100);

        assertTrue(userOptional.isEmpty());
    }

    @Test
    void testFindByUsernameContainingIgnoreCase_Success() {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase("user");

        assertEquals(2, users.size());
    }

    @Test
    void testFindByUsernameContainingIgnoreCase_NotFound() {
        List<User> users = userRepository.findByUsernameContainingIgnoreCase("nonexistent");

        assertTrue(users.isEmpty());
    }

    @Test
    void testFindAll_Success() {
        List<User> users = userRepository.findAll();

        assertEquals(2, users.size());
    }

    @Test
    void testFindUserByEmail_Success() {
        Optional<User> userOptional = userRepository.findUserByEmail("user1@example.com");

        assertTrue(userOptional.isPresent());
        assertEquals("user1@example.com", userOptional.get().getEmail());
    }

    @Test
    void testFindUserByEmail_NotFound() {
        Optional<User> userOptional = userRepository.findUserByEmail("nonexistent@example.com");

        assertTrue(userOptional.isEmpty());
    }

}