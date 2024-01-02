package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.service.PostService;
import com.artcon.artcon_back.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.artcon.artcon_back.service.FirebaseUserService;


import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PostService postService;

    @Autowired
    private FirebaseUserService firebaseUserService;


    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer userId){
        try {
            User user = userService.findUserById(userId);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable Integer userId,
                                           @RequestParam(required = false) MultipartFile picture,
                                           @RequestParam(required = false) MultipartFile banner,
                                           @ModelAttribute UpdateUserRequest updateUserRequest) {
        try {
            updateUserRequest.setPicture(picture);
            updateUserRequest.setBanner(banner);
            userService.updateUser(userId, updateUserRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/update-interest/{userId}")
    public ResponseEntity<Void> setUserInterests(@PathVariable Integer userId,@RequestBody List<Long> interestId){
        try{
            userService.selectInterest(userId, interestId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam("query") String query) {
        return ResponseEntity.ok(userService.searchUsers(query));
    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @RequestBody RegisterRequest request
    ){
        try {
            LoginResponse loginResponse = userService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        try {
            LoginResponse loginResponse = userService.login(request);
            return ResponseEntity.status(HttpStatus.OK).body(loginResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @PostMapping("/{userId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Integer userId,
            @RequestParam("picture") MultipartFile file) {
        try {
            userService.uploadProfilePicture(userId, file);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //get all portfolio posts for a user
    @GetMapping("/{userId}/portfolio")
    public ResponseEntity<List<PortfolioPost>> getPortfolioPosts(@PathVariable Integer userId) {
        try {
            List<PortfolioPost> portfolioPosts = userService.getPortfolioPosts(userId);

            return ResponseEntity.ok(portfolioPosts);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}/home")
    public ResponseEntity<List<Post>> getHome(@PathVariable Integer userId){
        try{
//            List<Post> posts = postService.findAllPosts();
            List<Post> posts = userService.getHomeFeed(userId);
            return ResponseEntity.ok(posts);
        } catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e){
            System.out.println(e.getCause());
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
