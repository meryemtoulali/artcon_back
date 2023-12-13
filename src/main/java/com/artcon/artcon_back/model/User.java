package com.artcon.artcon_back.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_firstname")
    private String firstname;

    @Column(name = "user_lastname")
    private String lastname;

    @Column(name = "user_username", unique = true)
    private String username;

    @Column(name = "user_email", unique = true)
    private String email;

    @Column(name = "user_password_hash")
    private String passwordHash;

    @Column(name = "user_type")
    private String type;

    @Column(name = "user_birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "user_picture")
    private String picture;

    @Column(name = "user_banner")
    private String banner;

    @Column(name = "user_phone_number")
    private String phoneNumber;

    @Column(name = "user_bio", length = 1000) // Set the maximum length as needed
    private String bio;

    @Column(name = "user_followers_count")
    private Integer followersCount;

    @Column(name = "user_following_count")
    private Integer followingCount;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id" , referencedColumnName = "user_id")
    private List<Post> posts ;

    @ManyToMany
    @JoinTable(name = "user_interest",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    private List<Interest> interests;


    public User(String login, String password) {
        this.username = login;
        this.passwordHash = password;
    }

    public User() { }

    public User(Integer id, String firstname, String lastname, String username, String email, String passwordHash, String type, Date birthday, String picture, String banner, String phoneNumber, String bio, Integer followersCount, Integer followingCount, List<Post> posts, List<Interest> interests) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.type = type;
        this.birthday = birthday;
        this.picture = picture;
        this.banner = banner;
        this.phoneNumber = phoneNumber;
        this.bio = bio;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.posts = posts;
        this.interests = interests;
    }

    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(Integer followersCount) {
        this.followersCount = followersCount;
    }

    public Integer getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(Integer followingCount) {
        this.followingCount = followingCount;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
