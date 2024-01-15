package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.ArtistType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ArtistTypeRepositoryTest {

    @Autowired
    private ArtistTypeRepository artistTypeRepository;

    @Test
    void findArtistTypeByIdSuccessful() {
        ArtistType savedArtistType = artistTypeRepository.save(new ArtistType(1L,"Painter"));

        Long artistTypeId = savedArtistType.getId();

        Optional<ArtistType> foundArtistTypeOptional = artistTypeRepository.findArtistTypeById(artistTypeId);

        assertTrue(foundArtistTypeOptional.isPresent());
        assertEquals("Painter", foundArtistTypeOptional.get().getArtist_type_name());
    }

    @Test
    void findArtistTypeByIdNotFound() {
        Optional<ArtistType> foundArtistTypeOptional = artistTypeRepository.findArtistTypeById(100L);

        assertTrue(foundArtistTypeOptional.isEmpty());
    }
}