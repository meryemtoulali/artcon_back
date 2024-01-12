package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    @Query("SELECT post FROM Post post WHERE " +
            "LOWER(post.description) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " OR LOWER(post.interest.interest_name) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " OR LOWER(post.user.username) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " OR LOWER(post.user.firstname) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " OR LOWER(post.user.lastname) LIKE LOWER(CONCAT('%', :query, '%'))" +
            " OR LOWER(CONCAT(post.user.firstname, ' ', post.user.lastname)) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Post> searchPosts(String query);

}