package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest,Long> {
    Optional<Interest> findInterestById(Long Id);
}
