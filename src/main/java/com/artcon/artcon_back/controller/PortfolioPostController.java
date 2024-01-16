package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.LikeRequest;
import com.artcon.artcon_back.model.LikeResponse;
import com.artcon.artcon_back.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PortfolioPostController {

    @Autowired
    LikePortfolioPostService likeService;
    @Autowired
    PortfolioPostService postService;
    @Autowired
    UserService userService;

    @PostMapping("/portfolioPostLike")
    public LikeResponse likePortfolioPost(@RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        likeService.likePortfolioPost(userId, postId);
        LikeResponse res = new LikeResponse();
        res.setSuccess(true);
        res.setMessage("Portfolio Post liked successfully");
        return res;
    }
    @PostMapping("/portfolioPostDislike")
    public LikeResponse dislikePortfolioPost( @RequestBody LikeRequest request) {
        Integer userId = request.getUser_id();
        Integer postId = request.getPost_id();
        likeService.dislikePortfolioPost(userId, postId);
        LikeResponse res = new LikeResponse();
        res.setSuccess(true);
        res.setMessage("Portfolio Post disliked successfully");
        return res;
    }

    @GetMapping("/hasUserLikedPortfolioPost")
    public boolean hasUserLikedPortfolioPost(@RequestParam("userId") Integer userId,
                                    @RequestParam("postId") Integer postId) {
        boolean userLikedPost = likeService.hasUserLikedPortfolioPost(userId, postId);
        return userLikedPost;
    }

}
