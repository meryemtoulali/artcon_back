package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Component
public class LikePortfolioPostService {
    @Autowired
    PortfolioPostLikeRepository likeRepo;
    @Autowired
    PortfolioPostRepository portfolioPostRepository;
    @Autowired
    PortfolioPostService portfolioPostService;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public void likePortfolioPost(Integer userId, Integer postId) {
        LikePortfolioPost likePortfolioPost = new LikePortfolioPost();

        PortfolioPost post = portfolioPostService.getPortfolioPost(postId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        likePortfolioPost.setUser(user);
        likePortfolioPost.setPortfolioPost(post);

        post.setLikes(post.getLikes() + 1);

        LikePortfolioPost result= likeRepo.save(likePortfolioPost);

        portfolioPostRepository.save(post);
    }

    public void deletePostLike(Integer userId, Integer postId) {
        likeRepo.deleteByUserIdAndPostId(userId, postId);
    }

    @Transactional
    public void dislikePortfolioPost(Integer userId, Integer postId) {
        PortfolioPost post = portfolioPostService.getPortfolioPost(postId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        deletePostLike(userId, postId);

        post.setLikes(post.getLikes() - 1);
        portfolioPostRepository.save(post);
    }

    public boolean hasUserLikedPortfolioPost(Integer userId, Integer postId) {
        return likeRepo.existsByUser_IdAndPortfolioPost_Id(userId, postId);
    }
}
