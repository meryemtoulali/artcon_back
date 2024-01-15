package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.service.InterestService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class InterestControllerTest {

    @Mock
    private InterestService interestService;

    @InjectMocks
    private InterestController interestController;

    @Test
    void getAllInterestSuccessful() {
        List<Interest> mockInterests = Arrays.asList(new Interest(1L, "Painting",null), new Interest(2L, "Photography",null));
        when(interestService.findAllInterest()).thenReturn(mockInterests);

        ResponseEntity<List<Interest>> responseEntity = interestController.getAllInterest();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockInterests, responseEntity.getBody());
    }

    @Test
    void getInterestByIdSuccessful() {
        Long interestId = 1L;
        Interest mockInterest = new Interest(interestId, "Painting", null);
        when(interestService.findInterestById(interestId)).thenReturn(mockInterest);

        ResponseEntity<Interest> responseEntity = interestController.getInterestById(interestId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockInterest, responseEntity.getBody());
    }

    @Test
    void addInterestSuccessful() {
        Interest interestToAdd = new Interest(null, null, null);

        Interest addedInterest = new Interest(1L, "Sculpture", null);
        when(interestService.addInterest(Mockito.any(Interest.class))).thenReturn(addedInterest);

        ResponseEntity<Interest> responseEntity = interestController.addInterest(interestToAdd);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(addedInterest, responseEntity.getBody());
    }
}