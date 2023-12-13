package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.repository.InterestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterestService {
    private final InterestRepository interestRepository;

    public InterestService(InterestRepository interestRepository) {
        this.interestRepository = interestRepository;
    }

    public Interest addInterest(Interest interest){
        return interestRepository.save(interest);
    }

    public List<Interest> findAllInterest(){
        return interestRepository.findAll();
    }

    public Interest findInterestById(Long id){
        return interestRepository.findInterestById(id).orElseThrow(
                () -> new RuntimeException("Interest not found")
        );
    }
}