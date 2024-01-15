package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostLike;
import com.artcon.artcon_back.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    private User user1;
    private User user2;
    private Post post1;
    private Post post2;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.builder()
                .username("user1")
                .email("user1@example.com")
                .build());

        user2 = userRepository.save(User.builder()
                .username("user2")
                .email("user2@example.com")
                .build());

        post1 = postRepository.save(Post.builder()
                .user(user1)
                .description("Post 1")
                .likes(0)
                .build());

        post2 = postRepository.save(Post.builder()
                .user(user2)
                .description("Post 2")
                .likes(0)
                .build());

        likeRepository.save(PostLike.builder().user(user1).post(post1).build());
        likeRepository.save(PostLike.builder().user(user2).post(post1).build());
        likeRepository.save(PostLike.builder().user(user2).post(post2).build());
    }

    @Test
    void testSavePostLike_Success() {
        PostLike postLike = PostLike.builder().user(user1).post(post2).build();
        likeRepository.save(postLike);

        assertNotNull(postLike.getId());
    }

    @Test
    void testDeletePostLike_Success() {
        assertTrue(likeRepository.existsByUser_IdAndPost_Id(user1.getId(), post1.getId()));

        likeRepository.deletePostLike(user1.getId(), post1.getId());

        assertFalse(likeRepository.existsByUser_IdAndPost_Id(user1.getId(), post1.getId()));
    }

    @Test
    void testExistsByUserAndPost_Success() {
        assertTrue(likeRepository.existsByUser_IdAndPost_Id(user2.getId(), post2.getId()));
        assertFalse(likeRepository.existsByUser_IdAndPost_Id(user1.getId(), post2.getId()));
    }
}
