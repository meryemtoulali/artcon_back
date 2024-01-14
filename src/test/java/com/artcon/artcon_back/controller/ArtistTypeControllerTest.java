package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.service.ArtistTypeService;
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
class ArtistTypeControllerTest {
    @Mock
    private ArtistTypeService artistTypeService;

    @InjectMocks
    private ArtistTypeController artistTypeController;

    @Test
    void getAllArtistTypeSuccessful() {
        List<ArtistType> mockArtistTypes = Arrays.asList(new ArtistType(1L, "Painter"), new ArtistType(2L, "Writer"));
        when(artistTypeService.findAllArtistType()).thenReturn(mockArtistTypes);

        ResponseEntity<List<ArtistType>> responseEntity = artistTypeController.getAllArtistType();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockArtistTypes, responseEntity.getBody());
    }

    @Test
    void getArtistTypeByIdSuccessful() {
        Long artistTypeId = 1L;
        ArtistType mockArtistType = new ArtistType(artistTypeId, "Painter");
        when(artistTypeService.findArtistTypeById(artistTypeId)).thenReturn(mockArtistType);

        ResponseEntity<ArtistType> responseEntity = artistTypeController.getArtistTypeById(artistTypeId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockArtistType, responseEntity.getBody());
    }

    @Test
    void addArtistTypeSuccessful() {
        ArtistType artistTypeToAdd = new ArtistType(null, null);

        ArtistType addedArtistType = new ArtistType(1L, "Photographer");
        when(artistTypeService.addArtistType(Mockito.any(ArtistType.class))).thenReturn(addedArtistType);

        ResponseEntity<ArtistType> responseEntity = artistTypeController.addArtistType(artistTypeToAdd);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(addedArtistType, responseEntity.getBody());
    }
}