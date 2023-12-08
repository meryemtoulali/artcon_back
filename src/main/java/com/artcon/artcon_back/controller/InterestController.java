package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.service.InterestService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interest")
public class InterestController {
    private final InterestService interestService;


    public InterestController(InterestService interestService) {
        this.interestService = interestService;
    }

    // Get All
    @GetMapping("/all")
    public ResponseEntity<List<Interest>> getAllInterest(){
        List<Interest> interests = interestService.findAllInterest();
        return new ResponseEntity<>(interests,HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Interest> getInterestById(@PathVariable("id") Long id){
        Interest interest = interestService.findInterestById(id);
        return new ResponseEntity<>(interest,HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Interest> addInterest(Interest interest){
        Interest newInterest = interestService.addInterest(interest);
        return new ResponseEntity<>(newInterest,HttpStatus.OK);
    }
}
