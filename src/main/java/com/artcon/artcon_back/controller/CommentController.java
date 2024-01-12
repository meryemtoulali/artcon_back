package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.model.LikeRequest;
import com.artcon.artcon_back.model.LikeRes;
import com.artcon.artcon_back.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/comment")
    public Comment comment(@RequestBody CommentRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        String content = request.getContent();

        Comment res = commentService.addComment(request);

        return res;
    }
}
