package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment save(Comment comment);
}
