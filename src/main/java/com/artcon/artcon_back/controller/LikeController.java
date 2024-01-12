package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.service.LikeService;
import com.artcon.artcon_back.service.PostService;
import com.artcon.artcon_back.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

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
    public LikeRes likePost( @RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        //Post post = postService.getPost(postId);
        likeService.likePost(userId, postId);
        LikeRes res = new LikeRes();
        res.setSuccess(true);
        res.setMessage("Post liked successfully");
        return res;
    }
    @PostMapping("/dislike")
    public LikeRes dislikePost( @RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        //Post post = postService.getPost(postId);
        likeService.dislikePost(userId, postId);

        LikeRes res = new LikeRes();
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
