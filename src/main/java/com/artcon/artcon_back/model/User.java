package com.artcon.artcon_back.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"user\"")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrement
    @Column(name = "id")
    private Integer id;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;
    @Column(name = "username", unique = true)
    private String username;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "password_hash")
    private String password_hash;
    @Column(name = "type")
    private String type;
    @Column(name = "title")
    private String title;
    @Column(name = "gender")
    private String gender;
    @Column(name = "phone_number")
    private String phone_number;
    @Column(name = "birthday")
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Column(name = "profile_picture")
    private String picture;
    @Column(name = "profile_banner")
    private String banner;
    @Column(name = "bio", length = 1000)
    private String bio;
    @Column(name = "location")
    private String location;
    @Column(name = "followers_count")
    private Integer followers_count;
    @Column(name = "following_count")
    private Integer following_count;
    @JsonIgnore // Break the circular reference
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PortfolioPost> portfolioPosts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonManagedReference
    //@JsonIgnore
    private List<Post> posts;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority(role.name()));
        if (role != null) {
            return List.of(new SimpleGrantedAuthority(role.name()));
        } else {
            // If role is null, return an empty list or some default authorities
            return Collections.emptyList();
        }
    }

    public void addPortfolioPost(PortfolioPost portfolioPost) {
        portfolioPosts.add(portfolioPost);
        portfolioPost.setUser(this);
    }

    public void removePortfolioPost(PortfolioPost portfolioPost) {
        portfolioPosts.remove(portfolioPost);
        portfolioPost.setUser(null);
    }

    @Override
    public String getPassword() {
        return this.password_hash;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public void setRole(Role role) {
        this.role = role;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }


    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setPassword_hash(String passwordHash) {
        this.password_hash = passwordHash;
    }


    public void setType(String type) {
        this.type = type;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }


    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }


    public void setLocation(String location) {
        this.location = location;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setFollowers_count(Integer followers_count) {
        this.followers_count = followers_count;
    }


    public void setFollowing_count(Integer following_count) {
        this.following_count = following_count;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
