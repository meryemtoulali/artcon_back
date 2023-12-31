package com.artcon.artcon_back.service;

import com.artcon.artcon_back.config.JwtService;
import com.artcon.artcon_back.model.PortfolioPost;
import com.artcon.artcon_back.model.UpdateUserRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest

public class UserServiceTest {


    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private FileStorageService fileStorageService;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAllUsers() {
        // Mock data
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = userService.findAllUsers();
        assertNotNull(result);
        assertEquals(userList, result);
    }

    @Test
    void testFindUserById() {
        User expectedUser = new User();
        expectedUser.setId(1);
        expectedUser.setUsername("john_doe");

        when(userRepository.findUserById(1)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.findUserById(1);
        assertNotNull(actualUser);
        assertEquals(expectedUser.getId(), actualUser.getId());
        assertEquals(expectedUser.getUsername(), actualUser.getUsername());
    }

    @Test
    void testUpdateUser() {
        // Mock data
        Integer userId = 1;
        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        try {
            when(fileStorageService.saveFile(any(MultipartFile.class))).thenReturn("fileUrl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        updateUserRequest.setBio("New Bio");
        updateUserRequest.setFirstname("New Firstname");
        updateUserRequest.setLastname("New Lastname");
        updateUserRequest.setLocation("New Location");


        assertDoesNotThrow(() -> userService.updateUser(userId, updateUserRequest));
        verify(userRepository, times(1)).save(user);
        assertEquals(updateUserRequest.getBio(), user.getBio());
        assertEquals(updateUserRequest.getFirstname(), user.getFirstname());
        assertEquals(updateUserRequest.getLastname(), user.getLastname());
        assertEquals(updateUserRequest.getLocation(), user.getLocation());
    }

    @Test
    void testUploadProfilePicture() {
        // Mock data
        Integer userId = 1;
        User user = new User();
        user.setId(userId);
        when(userRepository.findUserById(userId)).thenReturn(Optional.of(user));
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.jpg", "image/jpeg", "Some image content".getBytes());
        try {
            when(fileStorageService.saveFile(any(MultipartFile.class))).thenReturn("fileUrl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertDoesNotThrow(() -> userService.uploadProfilePicture(userId, mockFile));
        verify(userRepository, times(1)).save(user);
        assertEquals("fileUrl", user.getPicture());
    }

    @Test
    void testGetPortfolioPosts() {
        // Mock data
        Integer userId = 1;
        User user = new User();
        user.setId(userId);

        PortfolioPost post1 = new PortfolioPost();
        post1.setId(1);
        post1.setTitle("Post 1");

        PortfolioPost post2 = new PortfolioPost();
        post2.setId(2);
        post2.setTitle("Post 2");

        user.setPortfolioPosts(Arrays.asList(post1, post2));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Test the method
        List<PortfolioPost> result = userService.getPortfolioPosts(userId);

        // Verify
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(post1, result.get(0));
        assertEquals(post2, result.get(1));
    }

}
