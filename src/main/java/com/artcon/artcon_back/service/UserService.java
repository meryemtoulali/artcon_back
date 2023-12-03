package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FileStorageService fileStorageService;

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

    public void uploadProfilePicture(Long userId, MultipartFile file) {
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        // Save the file and get the file URL
        String fileUrl;
        try {
            fileUrl = fileStorageService.saveFile(file);
            user.setPicture(fileUrl);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
