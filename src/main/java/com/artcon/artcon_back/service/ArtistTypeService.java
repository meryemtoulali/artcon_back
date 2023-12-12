package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.repository.ArtistTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArtistTypeService {
    private final ArtistTypeRepository artistTypeRepository;

    public ArtistTypeService(ArtistTypeRepository artistTypeRepository) {
        this.artistTypeRepository = artistTypeRepository;
    }

    //Insert an artistType
    public ArtistType addArtistType(ArtistType artistType){
        return artistTypeRepository.save(artistType);
    }

    //Select all artistType
    public List<ArtistType> findAllArtistType() {
        return artistTypeRepository.findAll();
    }

    // Select artistType by ID
    public ArtistType findArtistTypeById(Long id){
        return artistTypeRepository.findArtistTypeById(id).orElseThrow(
                () -> new RuntimeException("Artist type not found")
        );
    }
}
