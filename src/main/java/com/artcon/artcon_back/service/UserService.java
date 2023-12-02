package com.artcon.artcon_back.service;

import com.artcon.artcon_back.config.JwtService;
import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    //Insert a user
    /*public User addUser(User user){
        return userRepository.save(user);
    }*/

    //Select user by
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    //Select user by ID
    public User findUserById(Integer id){
        return userRepository.findUserById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    public LoginResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .username(request.getUsername())
                .email(request.getEmail())
                .location(request.getLocation())
                .birthday(request.getBirthday())
                .gender(request.getGender())
                .phonenumber(request.getPhonenumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().token(jwtToken).build();
    }
    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().token(jwtToken).build();
    }
}
