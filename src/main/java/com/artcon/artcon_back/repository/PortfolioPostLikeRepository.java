package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.LikePortfolioPost;
import com.artcon.artcon_back.model.PostLike;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PortfolioPostLikeRepository extends JpaRepository<LikePortfolioPost ,Integer>  {
    LikePortfolioPost save(LikePortfolioPost likePortfolioPost);

    @Transactional
    @Modifying
    @Query("DELETE FROM LikePortfolioPost lpp WHERE lpp.user.id = :userId AND lpp.portfolioPost.id = :postId")
    void deleteByUserIdAndPostId(Integer userId, Integer postId);

    boolean existsByUser_IdAndPortfolioPost_Id(Integer userId, Integer postId);

}
