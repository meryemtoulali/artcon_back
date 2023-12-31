package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.service.FollowersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/followers")
public class FollowersController {

    @Autowired
    private FollowersService followersService;

    @PostMapping("/follow/{followerId}/{followingId}")
    public ResponseEntity<String> followUser(
            @PathVariable Integer followerId,
            @PathVariable Integer followingId) {
        followersService.followUser(followerId, followingId);
        return ResponseEntity.ok("User followed successfully");
    }

    @DeleteMapping("/unfollow/{followerId}/{followingId}")
    public ResponseEntity<String> unfollowUser(
            @PathVariable Integer followerId,
            @PathVariable Integer followingId) {
        followersService.unfollowUser(followerId, followingId);
        return ResponseEntity.ok("User unfollowed successfully");
    }

    @GetMapping("/check/{followerId}/{followingId}")
    public ResponseEntity<Boolean> checkIfUserFollows(
            @PathVariable Integer followerId,
            @PathVariable Integer followingId) {
        boolean isFollowing = followersService.checkIfUserFollows(followerId, followingId);
        return ResponseEntity.ok(isFollowing);
    }
}
