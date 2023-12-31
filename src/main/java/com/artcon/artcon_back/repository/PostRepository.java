package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post ,Integer> {
    List<Post> findAll();
    Post findPostById(Integer Id);
    Post save(Post post);
    void deleteById(Integer post_id);
    List<Post> findByUserId(Integer user_id);
    @Query("SELECT p FROM Post p JOIN User u ON p.user = u JOIN Followers f ON f.following = u WHERE f.follower.id = :userId" +
            " UNION " +
            "SELECT p FROM Post p JOIN User u ON p.interest IN elements(u.interestList) WHERE u.id = :userId AND p.user.id != :userId")
    List<Post> findPostsByUserInterestList(@Param("userId") Integer userId);
}