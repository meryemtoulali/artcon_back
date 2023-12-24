package com.artcon.artcon_back.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"interest\"")

public class Interest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private Long Id;

    @Column(name = "interest_name")
    private String interest_name;


 //   @OneToMany(cascade = CascadeType.ALL)
    //@JoinColumn(name = "interest_id" , referencedColumnName = "interest_id")
    //private List<Post> posts;


    @OneToMany(mappedBy = "interest", cascade = CascadeType.ALL)
    private List<Post> posts;

    public Interest(Long id, String interest_name, List<Post> posts) {
        Id = id;
        this.interest_name = interest_name;
        this.posts = posts;
    }

    public Interest() {

    }


    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
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
