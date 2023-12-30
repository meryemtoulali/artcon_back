package com.artcon.artcon_back.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String interest_name;

    @OneToMany(mappedBy = "interest", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Interest(Long Id, String interest_name, List<Post> posts) {
        this.Id = Id;
        this.interest_name = interest_name;
        this.posts = posts;
    }

    public Interest() {

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id = id;
    }

    public String getInterest_name() {
        return interest_name;
    }

    public void setInterest_name(String interest_name) {
        this.interest_name = interest_name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
