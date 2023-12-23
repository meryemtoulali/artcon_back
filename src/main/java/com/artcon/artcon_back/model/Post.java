package com.artcon.artcon_back.model;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"post\"")

public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @Column(name = "description")
    private String description;

    public Post(Integer id, String description, List<MediaFile> mediaFiles, Integer likes, Date date) {
        this.id = id;
        this.description = description;
        this.mediaFiles = mediaFiles;
        this.likes = likes;
        this.date = date;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id" , referencedColumnName = "post_id")
    private List<MediaFile> mediaFiles;

    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }

    @Column(name = "likes")
    private Integer likes;
    @Column(name = "dateTime")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private static User user;


    public void setLikes(Integer likes) {
        this.likes = likes;
    }


    @ManyToOne
    @JoinColumn(name = "interest_id")
    private static Interest interest;


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        Post.user = user;
    }

    public static Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        Post.interest = interest;
    }

    public Post() { }

}