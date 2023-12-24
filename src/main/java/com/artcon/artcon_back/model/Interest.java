package com.artcon.artcon_back.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String interest_name;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "user_interest",
            joinColumns = @JoinColumn(name = "interest_Id"),
            inverseJoinColumns = @JoinColumn(name = "user_Id"))
    private List<User> interested = new ArrayList<>();

    public Interest(Long Id, String interest_name) {
        this.Id = Id;
        this.interest_name = interest_name;
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

    public List<User> getInterested() {
        return interested;
    }

    public void setInterested(List<User> interested) {
        this.interested = interested;
    }
}
