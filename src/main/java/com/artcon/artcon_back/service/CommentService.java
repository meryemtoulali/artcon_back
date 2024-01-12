package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.CommentRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class CommentService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    CommentRepository commentRepository;

    @Transactional
    public Comment addComment(CommentRequest commentRequest){
        Date date = new Date();
        Integer comment_user_id = commentRequest.getUser_id();
        User user = userRepository.findUserById(comment_user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Integer comment_post_id = commentRequest.getPost_id();
        Post post = postService.getPost(comment_post_id);
        String content = commentRequest.getContent();

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setContent(content);
        comment.setDate(date);

        Comment result = commentRepository.save(comment);
        return result;
    }
}
