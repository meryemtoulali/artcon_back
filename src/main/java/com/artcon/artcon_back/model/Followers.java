package com.artcon.artcon_back.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "\"followers\"")
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincrement
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "follower_id")
    private User follower;
    @ManyToOne
    @JoinColumn(name = "following_id")
    private User following;

    public Followers(User follower, User following) {
        this.follower = follower;
        this.following = following;
    }
}
