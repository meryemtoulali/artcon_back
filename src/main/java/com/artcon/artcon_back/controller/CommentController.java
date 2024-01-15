package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.repository.CommentRepository;
import com.artcon.artcon_back.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @PostMapping("/comment")
    public Comment comment(@RequestBody CommentRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        String content = request.getContent();

        Comment res = commentService.addComment(request);

        return res;
    }
    @GetMapping("comment/byPost/{postId}")
    public List<Comment> getCommentsByPostId(@PathVariable Integer postId) {
        return commentRepository.findByPost_Id(postId);
    }

    @DeleteMapping("/comment/delete/{commentId}")
    public String deleteComment(@PathVariable Integer commentId) {
        try {
            commentRepository.deleteById(commentId);
            return "Comment deleted successfully";
        } catch (Exception e) {
            return "Error deleting comment: " + e.getMessage();
        }
    }
}
