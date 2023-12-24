package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
