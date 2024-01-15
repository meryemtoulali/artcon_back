package com.artcon.artcon_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Entity
@Builder
@Table(name = "\"post\"")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Integer id;

    @Column(name = "description")
    private String description;

    //@JsonBackReference
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "interest_id")
    private Interest interest;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id" , referencedColumnName = "post_id")
    private List<MediaFile> mediaFiles;

    public Post(Integer id, String description, User user, Interest interest, List<MediaFile> mediaFiles, Integer likes, Date date, List<PostLike> postLikes, Integer comments_count, List<Comment> comments) {
        this.id = id;
        this.description = description;
        this.user = user;
        this.interest = interest;
        this.mediaFiles = mediaFiles;
        this.likes = likes;
        this.date = date;
        this.postLikes = postLikes;
        this.comments_count = comments_count;
        this.comments = comments;
    }

    public Post() {
    }

    @Column(name = "likes")
    private Integer likes;
    @Column(name = "dateTime")
    private Date date;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikes;

    @Column(name = "comments_count")
    private  Integer comments_count;

    public List<PostLike> getPostLikes() {
        return postLikes;
    }

    public void setPostLikes(List<PostLike> postLikes) {
        this.postLikes = postLikes;
    }

    public Integer getComments_count() {
        return comments_count;
    }

    public void setComments_count(Integer comments_count) {
        this.comments_count = comments_count;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> comments;

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

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public List<MediaFile> getMediaFiles() {
        return mediaFiles;
    }

    public void setMediaFiles(List<MediaFile> mediaFiles) {
        this.mediaFiles = mediaFiles;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Interest getInterest() {
        return interest;
    }

    public void setInterest(Interest interest) {
        this.interest = interest;
    }

}