package com.artcon.artcon_back.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Artist_type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long artist_type_id;
    private String artist_type_name;

    public Artist_type(Long artist_type_id, String artist_type_name) {
        this.artist_type_id = artist_type_id;
        this.artist_type_name = artist_type_name;
    }

    public Artist_type() {

    }

    public Long getArtist_type_id() {
        return artist_type_id;
    }

    public void setArtist_type_id(Long artist_type_id) {
        this.artist_type_id = artist_type_id;
    }

    public String getArtist_type_name() {
        return artist_type_name;
    }

    public void setArtist_type_name(String artist_type_name) {
        this.artist_type_name = artist_type_name;
    }
}
