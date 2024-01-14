package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.service.LikeService;
import com.artcon.artcon_back.service.PostService;
import com.artcon.artcon_back.service.UserService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {
    @Autowired
    LikeService likeService;
    @Autowired
    PostService postService;
    @Autowired
    UserService userService;

    @PostMapping("/like")
    public LikeResponse likePost(@RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        likeService.likePost(userId, postId);
        LikeResponse res = new LikeResponse();
        res.setSuccess(true);
        res.setMessage("Post liked successfully");
        return res;
    }
    @PostMapping("/dislike")
    public LikeResponse dislikePost(@RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        likeService.dislikePost(userId, postId);

        LikeResponse res = new LikeResponse();
        res.setSuccess(true);
        res.setMessage("Post disliked successfully");
        return res;
    }

    @GetMapping("/hasUserLikedPost")
    public boolean hasUserLikedPost(@RequestParam("userId") Integer userId,
                                    @RequestParam("postId") Integer postId) {
        boolean userLikedPost = likeService.hasUserLikedPost(userId, postId);
        return userLikedPost;
    }

}
