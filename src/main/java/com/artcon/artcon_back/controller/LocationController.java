package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Location;
import com.artcon.artcon_back.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
public class LocationController {
    private LocationRepository locationRepository;

    @Autowired
    public LocationController(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @GetMapping("/all")
    public List<String> getLocations(){
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(Location::getName)
                .collect(Collectors.toList());
    }

    @PostMapping("/addlocations")
    public ResponseEntity<String> addLocations(@RequestBody List<Location> locations){
        if(locations.isEmpty()){
            return ResponseEntity.badRequest().body("Location list empty");
        }
        Set<String> locationNames = new HashSet<>();
        for (Location location : locations){
            if (location.getName() == null || location.getName().isEmpty()){
                return ResponseEntity.badRequest().body("Location name empty");
            }
            if (!locationNames.add(location.getName())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate city names");
            }
        }
        List<Location> savedLocations = locationRepository.saveAll(locations);
        // this code is creating a URI that points to the newly created city resource
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")//to include the identifier of the newly created resource in the URI path
                .buildAndExpand(savedLocations.get(0).getId())  // Assuming the first city in the list
                .toUri();
        return ResponseEntity.created(locationUri).build();
    }

}
