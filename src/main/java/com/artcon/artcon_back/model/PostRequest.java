package com.artcon.artcon_back.model;

import org.springframework.web.multipart.MultipartFile;

public class PostRequest {
    private Integer user_id;
    private String description;
    //private MultipartFile media;

    private Long interest_id;

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

    /*public MultipartFile getMedia() {
        return media;
    }

    public void setMedia(MultipartFile media) {
        this.media = media;
    }*/

    public Long getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Long interest_id) {
        this.interest_id = interest_id;
    }
}
