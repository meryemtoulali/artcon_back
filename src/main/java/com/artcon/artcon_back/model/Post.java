package com.artcon.artcon_back.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "\"post\"")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @Column(name = "description")
    private String description;
    @Column(name = "postImgUrl")
    private String postImgURL;
    @Column(name = "likes")
    private Integer likes;
    @Column(name = "dateTime")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private static User user;

    @Transient
    private Integer user_id;


    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    @ManyToOne
    @JoinColumn(name = "interest_id")
    private static Interest interest;

    @Transient
    private static Integer interest_id;

    public static Integer getInterest_id() {
        return interest_id;
    }

    public void setInterest_id(Integer interest_id) {
        this.interest_id = interest_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer post_id) {
        this.id = post_id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public static Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

    public Post() { }

    public Post(Integer id, String description, String postImgURL, Integer likes, Date date, User user, Integer user_id, Interest interest, Integer interest_id) {
        this.id = id;
        this.description = description;
        this.postImgURL = postImgURL;
        this.likes = likes;
        this.date = date;
        this.user = user;
        this.user_id = user_id;
        this.interest = interest;
        this.interest_id = interest_id;
    }
}
