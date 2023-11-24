package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Insert a user
    public User addUser(User user){
        return userRepository.save(user);
    }

    //Select user by
    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    //Select user by ID
    public User findUserById(Long id){
        return userRepository.findUserById(id).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

}
