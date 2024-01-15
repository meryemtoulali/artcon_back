package com.artcon.artcon_back.controller;
import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    private User createUser(int id, String username, String email) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }


    @Test
    void getAllUsers_ReturnsListOfUsers() throws Exception {
        List<User> users = Arrays.asList(
                createUser(1, "user1", "user1@example.com"),
                createUser(2, "user2", "user2@example.com")
        );
        when(userService.findAllUsers()).thenReturn(users);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].username").value("user1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].username").value("user2"));
    }

    @Test
    void getUserById_ExistingUserId_ReturnsUser() throws Exception {
        User user = createUser(1, "user1", "user1@example.com");
        when(userService.findUserById(1)).thenReturn(user);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("user1"));
    }

    @Test
    void getUserById_NonExistingUserId_ReturnsNotFound() throws Exception {
        when(userService.findUserById(1)).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/1"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void deleteUser_ValidUserId_ReturnsOk() throws Exception {
        int userId = 1;

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{userId}", userId))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void getPortfolioPosts_ValidUserId_ReturnsOk() throws Exception {
        int userId = 1;
        List<PortfolioPost> portfolioPosts = Arrays.asList(
                new PortfolioPost(), new PortfolioPost()
        );
        when(userService.getPortfolioPosts(userId)).thenReturn(portfolioPosts);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{userId}/portfolio", userId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void registerSuccessfulReturnsCreated() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("spencer")
                .lastname("reid")
                .gender("Male")
                .birthday(new Date())
                .email("spencer@gmail.com")
                .phonenumber("7775558982")
                .location("Rabat")
                .password("admin")
                .username("spence")
                .build();

        LoginResponse mockLoginResponse = new LoginResponse("token","spence",1);
        when(userService.register(Mockito.any(RegisterRequest.class))).thenReturn(mockLoginResponse);

        ResponseEntity<LoginResponse> responseEntity = userController.register(registerRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(mockLoginResponse, responseEntity.getBody());
    }

    @Test
    void registerFailureReturnsInternalServerError() {
        RegisterRequest registerRequest = RegisterRequest.builder()
                .firstname("spencer")
                .lastname("reid")
                .gender("Male")
                .birthday(new Date())
                .email("spencer@gmail.com")
                .phonenumber("7775558982")
                .location("Rabat")
                .password("admin")
                .username("spence")
                .build();

        when(userService.register(Mockito.any(RegisterRequest.class))).thenThrow(new RuntimeException("Registration failed"));

        ResponseEntity<LoginResponse> responseEntity = userController.register(registerRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    void loginSuccessfulReturnsOK() {
        LoginRequest loginRequest = new LoginRequest("spence","admin");

        LoginResponse mockLoginResponse = new LoginResponse("token","spence",1);
        when(userService.login(Mockito.any(LoginRequest.class))).thenReturn(mockLoginResponse);

        ResponseEntity<LoginResponse> responseEntity = userController.login(loginRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockLoginResponse, responseEntity.getBody());
    }

    @Test
    void login_LoginFailure_ReturnsInternalServerError() {
        LoginRequest loginRequest = new LoginRequest("spence","admin");

        when(userService.login(Mockito.any(LoginRequest.class))).thenThrow(new RuntimeException("Login failed"));

        ResponseEntity<LoginResponse> responseEntity = userController.login(loginRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
