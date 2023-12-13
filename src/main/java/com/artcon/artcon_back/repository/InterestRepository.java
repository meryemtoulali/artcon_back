package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest,Long> {
    Optional<Interest> findInterestById(Long Id);
    ArrayList<Interest> findAll();
    Interest save(Interest interest);
    void deleteById(Long interest_id);
}
