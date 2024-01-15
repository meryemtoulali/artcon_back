package com.artcon.artcon_back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "portfolio_post_like")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class LikePortfolioPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "portfolio_post_id")
    private PortfolioPost portfolioPost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public PortfolioPost getPortfolioPost() {
        return portfolioPost;
    }

    public void setPortfolioPost(PortfolioPost portfolioPost) {
        this.portfolioPost = portfolioPost;
    }

}
