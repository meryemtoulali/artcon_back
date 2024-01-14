package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.repository.InterestRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class InterestServiceTest {

    @Mock
    private InterestRepository interestRepository;

    @InjectMocks
    private InterestService interestService;

    @Test
    void testAddInterest() {
        Interest interestToAdd = new Interest(1L, "Test Interest",null);

        when(interestRepository.save(interestToAdd)).thenReturn(interestToAdd);

        Interest addedInterest = interestService.addInterest(interestToAdd);

        assertEquals(interestToAdd, addedInterest);

        verify(interestRepository, times(1)).save(interestToAdd);
    }

    @Test
    void testFindAllInterest() {
        // Mock data for the test
        List<Interest> expectedInterests = Arrays.asList(
                new Interest(1L, "Interest1",null),
                new Interest(2L, "Interest2",null),
                new Interest(3L, "Interest3",null)
        );

        when(interestRepository.findAll()).thenReturn(expectedInterests);

        List<Interest> actualInterests = interestService.findAllInterest();

        assertEquals(expectedInterests.size(), actualInterests.size());
        assertEquals(expectedInterests, actualInterests);

        verify(interestRepository, times(1)).findAll();
    }

    @Test
    void testFindInterestById_Success() {
        Long interestId = 1L;
        Interest expectedInterest = new Interest(interestId, "Test Interest",null);

        when(interestRepository.findInterestById(interestId)).thenReturn(Optional.of(expectedInterest));

        Interest actualInterest = interestService.findInterestById(interestId);

        assertEquals(expectedInterest, actualInterest);

        verify(interestRepository, times(1)).findInterestById(interestId);
    }

    @Test
    void testFindInterestById_NotFound() {
        Long nonExistentInterestId = 100L;

        when(interestRepository.findInterestById(nonExistentInterestId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> interestService.findInterestById(nonExistentInterestId));

        verify(interestRepository, times(1)).findInterestById(nonExistentInterestId);
    }

}