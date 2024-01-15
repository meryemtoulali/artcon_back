package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostLike;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.LikeRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LikeServiceTest {

    @InjectMocks
    private LikeService likeService;

    @Mock
    private LikeRepository likeRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostService postService;

    @Mock
    private UserRepository userRepository;

    @Test
    void testLikePost() {
        Integer userId = 1;
        Integer postId = 2;

        Post post = new Post();
        post.setId(postId);
        post.setLikes(0);

        User user = new User();
        user.setId(userId);

        PostLike postLike = new PostLike();
        postLike.setUser(user);
        postLike.setPost(post);

        when(userRepository.findUserById(userId)).thenReturn(java.util.Optional.of(user));

        when(postService.getPost(postId)).thenReturn(post);

        when(likeRepository.save(any(PostLike.class))).thenReturn(postLike);  // Use any() here

        likeService.likePost(userId, postId);

        assertEquals(1, post.getLikes());
        verify(postRepository, times(1)).save(post);

        verify(likeRepository, times(1)).save(any(PostLike.class));  // Use any() here
    }

    @Test
    void testDeletePostLike() {

        Integer userId = 1;
        Integer postId = 2;

        likeService.deletePostLike(userId, postId);

        verify(likeRepository, times(1)).deletePostLike(userId, postId);
    }

    @Test
    void testDislikePost() {
        Integer userId = 1;
        Integer postId = 2;

        Post post = new Post();
        post.setId(postId);
        post.setLikes(1);

        User user = new User();
        user.setId(userId);

        when(userRepository.findUserById(userId)).thenReturn(java.util.Optional.of(user));
        when(postService.getPost(postId)).thenReturn(post);

        when(likeRepository.existsByUser_IdAndPost_Id(userId, postId)).thenReturn(true);

        likeService.dislikePost(userId, postId);

        assertEquals(0, post.getLikes());
        verify(postRepository, times(1)).save(post);

        verify(likeRepository, times(1)).deletePostLike(userId, postId);
    }

    @Test
    void testHasUserLikedPost() {

        Integer userId = 1;
        Integer postId = 2;

        when(likeRepository.existsByUser_IdAndPost_Id(userId, postId)).thenReturn(true);

        boolean result = likeService.hasUserLikedPost(userId, postId);

        assertTrue(result);
    }
}
