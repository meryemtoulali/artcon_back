package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.ArtistType;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.service.ArtistTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/artistType")
public class ArtistTypeController {

    private final ArtistTypeService artistTypeService;


    public ArtistTypeController(ArtistTypeService artistTypeService) {
        this.artistTypeService = artistTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ArtistType>> getAllArtistType() {
        List<ArtistType> artistTypes = artistTypeService.findAllArtistType();
        return new ResponseEntity<>(artistTypes, HttpStatus.OK);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<ArtistType> getArtistTypeById(@PathVariable("id") Long id){
        ArtistType artistType = artistTypeService.findArtistTypeById(id);
        return new ResponseEntity<>(artistType, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ArtistType> addArtistType(@RequestBody ArtistType artistType){
        ArtistType newArtistType = artistTypeService.addArtistType(artistType);
        return new ResponseEntity<>(newArtistType,HttpStatus.CREATED);
    }
}
