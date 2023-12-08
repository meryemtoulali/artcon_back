package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.repository.ArtistTypeRepository;

import java.util.List;

public class ArtistTypeService {
    private final ArtistTypeRepository artist_typeRepository;

    public ArtistTypeService(ArtistTypeRepository artistTypeRepository) {
        artist_typeRepository = artistTypeRepository;
    }

    //Insert an artist_type
    public ArtistType addArtist_type(ArtistType artist_type){
        return artist_typeRepository.save(artist_type);
    }

    //Select all artist_type
    public List<ArtistType> findAllArtist_type() {
        return artist_typeRepository.findAll();
    }

    // Select artist_type by ID
    public ArtistType findArtist_typeById(Long id){
        return artist_typeRepository.findArtistTypeById(id).orElseThrow(
                () -> new RuntimeException("Artist type not found")
        );
    }
}
