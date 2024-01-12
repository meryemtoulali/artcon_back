package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostLike;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.LikeRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Component
public class LikeService {

    @Autowired
    LikeRepository likeRepo;
    @Autowired
    PostRepository postRepository;
    @Autowired
    PostService postService;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public void likePost(Integer userId, Integer postId) {
        PostLike postLike = new PostLike();

        Post post = postService.getPost(postId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        postLike.setUser(user);
        postLike.setPost(post);

        post.setLikes(post.getLikes() + 1);

        PostLike result= likeRepo.save(postLike);

        postRepository.save(post);
    }

    public void deletePostLike(Integer userId, Integer postId) {
        likeRepo.deletePostLike(userId, postId);
    }

    @Transactional
    public void dislikePost(Integer userId, Integer postId) {
        Post post = postService.getPost(postId);
        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        deletePostLike(userId, postId);

        post.setLikes(post.getLikes() - 1);
        postRepository.save(post);
    }

    public boolean hasUserLikedPost(Integer userId, Integer postId) {
        return likeRepo.existsByUser_IdAndPost_Id(userId, postId);
    }
}
