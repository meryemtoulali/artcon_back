package com.artcon.artcon_back.model;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;


public class PostRequest implements Serializable {
    private Integer user_id;
    private String description;
    private MultipartFile[] mediafiles;
    private Long interest_id;

    public PostRequest() { }

    public PostRequest(Integer user_id, String description, MultipartFile[] mediafiles, Long interest_id) {
        this.user_id = user_id;
        this.description = description;
        this.mediafiles = mediafiles;
        this.interest_id = interest_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile[] getMediafiles() {
        return mediafiles;
    }

    public void setMediafiles(MultipartFile[] mediafiles) {
        this.mediafiles = mediafiles;
    }

    public Long getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Long interest_id) {
        this.interest_id = interest_id;
    }
}