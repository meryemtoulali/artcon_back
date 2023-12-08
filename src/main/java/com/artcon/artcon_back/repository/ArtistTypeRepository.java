package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArtistTypeRepository extends JpaRepository<ArtistType,Long> {
    Optional<ArtistType> findArtistTypeById(Long id);
}
