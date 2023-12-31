package com.artcon.artcon_back.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Set;

@Data
public class UpdateUserRequest{
    // fields that can be updated in the user
    private String bio;
    private String firstname;
    private String lastname;
    private String location;
    private String gender;
    private String phone_number;
    private String title;
    private String type;
    private String username;
    private MultipartFile picture;
    private MultipartFile banner;
    private Date birthday;
}