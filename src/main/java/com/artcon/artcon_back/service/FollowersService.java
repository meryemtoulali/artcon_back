package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Followers;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.FollowersRepository;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FollowersService {

    @Autowired
    private FollowersRepository followersRepository;

    @Autowired
    private UserRepository userRepository;
    @Transactional

    public void followUser(Integer followerId, Integer followingId) {
        // Check if the user is not trying to follow themselves
        if (!followerId.equals(followingId)) {
            User follower = userRepository.findById(followerId).orElseThrow(
                    () -> new EntityNotFoundException("User not found"));
            User following = userRepository.findById(followingId).orElseThrow(
                    () -> new EntityNotFoundException("User not found"));
            Followers newFollower = new Followers(follower, following);

            follower.getFollowing().add(newFollower);
            following.getFollowers().add(newFollower);
            follower.setFollowing_count(follower.getFollowing_count() + 1);
            following.setFollowers_count(following.getFollowers_count() + 1);

            userRepository.save(follower);
            userRepository.save(following);

        }
    }
    @Transactional
    public void unfollowUser(Integer followerId, Integer followingId) {
        User follower = userRepository.findById(followerId).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        User following = userRepository.findById(followingId).orElseThrow(
                () -> new EntityNotFoundException("User not found"));
        Followers existingFollower = new Followers(follower, following);
        follower.getFollowing().remove(existingFollower);
        following.getFollowers().remove(existingFollower);
        follower.setFollowing_count(follower.getFollowing_count() - 1);
        following.setFollowers_count(following.getFollowers_count() - 1);

        userRepository.save(follower);
        userRepository.save(following);

        followersRepository.deleteByFollowerIdAndFollowingId(followerId, followingId);
    }

    public boolean checkIfUserFollows(Integer followerId, Integer followingId) {
        return followersRepository.existsByFollowerIdAndFollowingId(followerId, followingId);
    }
}
