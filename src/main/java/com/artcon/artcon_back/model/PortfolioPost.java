package com.artcon.artcon_back.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @Column(name = "likes")
    private Integer likes;

    @JsonIgnore
    @OneToMany(mappedBy = "portfolioPost", cascade = CascadeType.ALL)
    private List<LikePortfolioPost> likePortfolioPosts;

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
