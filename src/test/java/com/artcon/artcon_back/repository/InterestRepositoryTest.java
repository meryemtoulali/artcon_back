package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InterestRepositoryTest {

    @Autowired
    private InterestRepository interestRepository;

    @Test
    void findInterestByIdSuccessful() {
        Interest savedInterest = interestRepository.save(new Interest(1L, "Music",null));

        Long interestId = savedInterest.getId();

        Optional<Interest> foundInterestOptional = interestRepository.findInterestById(interestId);

        assertTrue(foundInterestOptional.isPresent());
        assertEquals("Music", foundInterestOptional.get().getInterest_name());
    }

    @Test
    void findInterestByIdNotFound() {
        Optional<Interest> foundInterestOptional = interestRepository.findInterestById(100L);

        assertTrue(foundInterestOptional.isEmpty());
    }
}