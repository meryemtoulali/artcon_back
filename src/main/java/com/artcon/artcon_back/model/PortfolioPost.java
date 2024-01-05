package com.artcon.artcon_back.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "portfolio_post")
public class PortfolioPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    private Date date;
    @Column(name = "title")
    private String title;
    @Column(name = "caption")
    private String caption;
    @Column(name = "media")
    private String media;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    // Transient fields for user information when retrieving a portfolio post
//    @Transient
//    private Integer userId;
//
//    @Transient
//    private String username;
//
//    @Transient
//    private String firstname;
//
//    @Transient
//    private String lastname;

}
