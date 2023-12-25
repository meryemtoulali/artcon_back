package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    Optional<Location> findLocationById(Integer id);

}
