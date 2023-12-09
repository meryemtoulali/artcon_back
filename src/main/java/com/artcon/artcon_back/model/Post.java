package com.artcon.artcon_back.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
@Entity
@Table(name = "\"post\"")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer post_id;

    private String userID;
    private String userName;

    private Integer category_id;
    private String category;
    private String description;
    private String postImgURL;

    private int likes;
    private Timestamp dateTime;

    public Post() { }

    public Post(Integer post_id, String userID, String userName, Integer category_id, String category,
                String description, String postImgURL, int likes, Timestamp dateTime) {
        super();
        this.post_id = post_id;
        this.userID = userID;
        this.userName = userName;
        this.category_id = category_id;
        this.category = category;
        this.description = description;
        this.postImgURL = postImgURL;
        this.likes = likes;
        this.dateTime = dateTime;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getPost_id() {
        return post_id;
    }

    public void setPost_id(Integer post_id) {
        this.post_id = post_id;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImgURL() {
        return postImgURL;
    }

    public void setPostImgURL(String postImgURL) {
        this.postImgURL = postImgURL;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }
}
