package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.repository.ArtistTypeRepository;
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
class ArtistTypeServiceTest {
    @Mock
    private ArtistTypeRepository artistTypeRepository;

    @InjectMocks
    private ArtistTypeService artistTypeService;

    @Test
    void testAddArtistType() {
        ArtistType artistTypeToAdd = new ArtistType(1L, "Test Artist Type");

        when(artistTypeRepository.save(artistTypeToAdd)).thenReturn(artistTypeToAdd);

        ArtistType addedArtistType = artistTypeService.addArtistType(artistTypeToAdd);

        assertEquals(artistTypeToAdd, addedArtistType);

        verify(artistTypeRepository, times(1)).save(artistTypeToAdd);
    }

    @Test
    void testFindAllArtistType() {
        List<ArtistType> expectedArtistTypes = Arrays.asList(
                new ArtistType(1L, "Type1"),
                new ArtistType(2L, "Type2"),
                new ArtistType(3L, "Type3")
        );

        when(artistTypeRepository.findAll()).thenReturn(expectedArtistTypes);

        List<ArtistType> actualArtistTypes = artistTypeService.findAllArtistType();

        assertEquals(expectedArtistTypes.size(), actualArtistTypes.size());
        assertEquals(expectedArtistTypes, actualArtistTypes);

        verify(artistTypeRepository, times(1)).findAll();
    }

    @Test
    void testFindArtistTypeById_Success() {
        Long artistTypeId = 1L;
        ArtistType expectedArtistType = new ArtistType(artistTypeId, "Test Artist Type");

        when(artistTypeRepository.findArtistTypeById(artistTypeId)).thenReturn(Optional.of(expectedArtistType));

        ArtistType actualArtistType = artistTypeService.findArtistTypeById(artistTypeId);

        assertEquals(expectedArtistType, actualArtistType);

        verify(artistTypeRepository, times(1)).findArtistTypeById(artistTypeId);
    }

    @Test
    void testFindArtistTypeById_NotFound() {
        Long nonExistentArtistTypeId = 100L;

        when(artistTypeRepository.findArtistTypeById(nonExistentArtistTypeId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> artistTypeService.findArtistTypeById(nonExistentArtistTypeId));

        verify(artistTypeRepository, times(1)).findArtistTypeById(nonExistentArtistTypeId);
    }
}