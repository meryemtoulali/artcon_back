package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.LoginRequest;
import com.artcon.artcon_back.model.LoginResponse;
import com.artcon.artcon_back.model.RegisterRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

//    @PostMapping("/add")
//    public ResponseEntity<User> addUser(@RequestBody User user){
//        User newUser = userService.addUser(user);
//        return new ResponseEntity<>(newUser,HttpStatus.CREATED);
//    }

//    @PostMapping("/login")
//    public LoginResponse login(@RequestBody LoginRequest request){
//        LoginResponse response = new LoginResponse();
//        response.setSuccess(true);
//        response.setMessage("Login successful");
//        return response;
//    }

    @PostMapping("/register")
    public ResponseEntity<LoginResponse> register(
            @RequestBody RegisterRequest request
    ){
        return ResponseEntity.ok(userService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(userService.login(request));
    }



    @PostMapping("/{userId}/upload-profile-picture")
    public ResponseEntity<String> uploadProfilePicture(
            @PathVariable Long userId,
            @RequestParam("picture") MultipartFile file) {
        userService.uploadProfilePicture(userId, file);
        return ResponseEntity.ok("Profile picture uploaded successfully");
    }
}
