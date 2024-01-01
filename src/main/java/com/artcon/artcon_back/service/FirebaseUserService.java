package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import org.springframework.stereotype.Service;

@Service
public class FirebaseUserService {

    public void createUserInFirebase(User user) {
        try {
            // Ensure you handle password securely (consider not storing plain-text passwords)
            UserRecord.CreateRequest request = new CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword_hash()) // Assuming 'password_hash' contains the hashed password
                    .setDisplayName(user.getFirstname() + " " + user.getLastname())
                    .setPhotoUrl(user.getPicture());


            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);
            // Handle successful user creation in Firebase if needed
        } catch (Exception e) {
            // Handle Firebase user creation failure
            // For example, you can throw a custom exception or log the error
            throw new RuntimeException("Error creating user in Firebase", e);
        }
    }
}
