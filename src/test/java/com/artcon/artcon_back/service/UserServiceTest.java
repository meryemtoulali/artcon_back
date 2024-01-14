package com.artcon.artcon_back.service;

import com.artcon.artcon_back.config.JwtService;
import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.UserRepository;
import com.artcon.artcon_back.token.Token;
import com.artcon.artcon_back.token.TokenRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private TokenRepository tokenRepository;

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
        // Print the hashCode of the mocked userRepository to identify if it's the same instance
        System.out.println("Mocked userRepository hashCode: " + userRepository.hashCode());

        // Print the hashCode of the userService to identify if it's the same instance
        System.out.println("Mocked userService hashCode: " + userService.hashCode());

        // Print the hashCode of the userService userRepository to identify if it's the same instance
        System.out.println("userService userRepository hashCode: " + userService.getUserRepository().hashCode());

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

    @Test
    void testRegister_Success() {
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
        User savedUser = User.builder()
                .id(1)
                .username("spence")
                .build();
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        String expectedToken = "help";
        when(jwtService.generateToken(any(User.class))).thenReturn(expectedToken);

        LoginResponse loginResponse = userService.register(registerRequest);

        assertEquals(expectedToken, loginResponse.getToken());
        assertEquals("spence", loginResponse.getUsername());

        verify(userRepository, times(1)).save(any(User.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testLogin_Success() {
        LoginRequest loginRequest = new LoginRequest("spence", "password");
        User existingUser = User.builder().id(1).username("spence").build();

        when(userRepository.findByUsername("spence")).thenReturn(Optional.of(existingUser));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");
        when(tokenRepository.findAllValidTokenByUser(1)).thenReturn(new ArrayList<>());

        LoginResponse loginResponse = userService.login(loginRequest);

        assertEquals("jwtToken", loginResponse.getToken());
        assertEquals("spence", loginResponse.getUsername());
        assertEquals(1, loginResponse.getUserId());

        verify(userRepository, times(1)).findByUsername("spence");
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(jwtService, times(1)).generateToken(any(User.class));
        verify(tokenRepository, times(1)).findAllValidTokenByUser(1);
        verify(tokenRepository, times(1)).save(any(Token.class));
    }

    @Test
    void testSearchUsers() {
        String query = "john";
        String title = "developer";
        String type = "employee";
        String location = "city";

        List<User> expectedUsers = new ArrayList<>();
        expectedUsers.add(User.builder().id(1).username("john_doe").title("Developer").type("Employee").location("City").build());
        expectedUsers.add(User.builder().id(2).username("john_smith").title("Developer").type("Contractor").location("City").build());

        when(userRepository.searchUser(query, title, type, location)).thenReturn(expectedUsers);

        List<User> actualUsers = userService.searchUsers(query, title, type, location);

        assertEquals(expectedUsers.size(), actualUsers.size());
        for (int i = 0; i < expectedUsers.size(); i++) {
            assertEquals(expectedUsers.get(i).getId(), actualUsers.get(i).getId());
        }

        verify(userRepository, times(1)).searchUser(query, title, type, location);
    }

}
