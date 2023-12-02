package com.artcon.artcon_back.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstname;
    private String lastname;
    private String gender;
    private String phonenumber;
    private Date birthday;
    private String location;
    private String username;
    private String email;
    private String password;
}
