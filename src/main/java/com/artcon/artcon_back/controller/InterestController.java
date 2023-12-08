package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.service.InterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/interest")
public class InterestController {
    private final InterestService interestService;


    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    // Get All

}
