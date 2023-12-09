package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PostRepository extends JpaRepository<Post ,Long> {

    ArrayList<Post> findAll();
    ArrayList<Post> save(Post post);
    void deleteById(int post_id);
}
