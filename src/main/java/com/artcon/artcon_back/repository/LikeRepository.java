package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<PostLike ,Integer> {
    PostLike save(PostLike postLike);

    @Modifying
    @Query("DELETE FROM PostLike pl WHERE pl.user.id = :userId AND pl.post.id = :postId")
    void deletePostLike(@Param("userId") Integer userId, @Param("postId") Integer postId);

    boolean existsByUser_IdAndPost_Id(Integer userId, Integer postId);

}
