package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class FirebaseUserService {

    private static final String DEFAULT_PROFILE_PICTURE_URL = "https://example.com/default-profile-picture.jpg";

    public void createUserInFirebase(User user) {
        try {
            // Ensure you handle password securely (consider not storing plain-text passwords)
            UserRecord.CreateRequest request = new CreateRequest()
                    .setEmail(user.getEmail())
                    .setPassword(user.getPassword_hash()) // Assuming 'password_hash' contains the hashed password
                    .setDisplayName(user.getFirstname() + " " + user.getLastname())
                    .setPhotoUrl(getProfilePictureUrl(user)); // Use the profile picture URL or default URL

            UserRecord userRecord = FirebaseAuth.getInstance().createUser(request);

            // Output success message (Optional)
            System.out.println("Successfully created user in Firebase: " + userRecord.getUid());
        } catch (Exception e) {
            // Handle Firebase user creation error
            System.err.println("Error creating user in Firebase: " + e.getMessage());
            throw new RuntimeException("Error creating user in Firebase", e);
        }
    }

    private String getProfilePictureUrl(User user) {
        // Check if the user has a profile picture URL set
        if (user.getPicture() != null && !user.getPicture().isEmpty()) {
            return user.getPicture();
        } else {
            // Return default or placeholder profile picture URL
            return DEFAULT_PROFILE_PICTURE_URL;
        }
    }
}
