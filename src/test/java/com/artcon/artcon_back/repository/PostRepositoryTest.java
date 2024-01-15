package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.PostRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    void testSavePost() {
        User user = new User();
        user.setUsername("testUser");

        Interest interest = new Interest();
        interest.setInterest_name("testInterest");

        Post post = Post.builder()
                .description("Test Post")
                .user(user)
                .interest(interest)
                .likes(0)
                .date(new Date())
                .build();

        Post savedPost = postRepository.save(post);

        assertNotNull(savedPost.getId());
        assertEquals("Test Post", savedPost.getDescription());
    }

    @Test
    void testFindPostById() {
        User user = new User();
        user.setUsername("testUser");

        User savedUser = userRepository.save(user);

        Interest interest = new Interest();
        interest.setInterest_name("testInterest");

        Interest savedInterest = interestRepository.save(interest);

        Post post = Post.builder()
                .description("Test Post")
                .user(user)
                .interest(savedInterest)
                .likes(0)
                .date(new Date())
                .build();

        Post savedPost = postRepository.save(post);

        Post foundPost = postRepository.findPostById(savedPost.getId());

        assertNotNull(foundPost);
        assertEquals(savedPost.getDescription(), foundPost.getDescription());
    }
    @Test
    void testFindAll() {
        Post post1 = createSamplePost("Post 1");
        Post post2 = createSamplePost("Post 2");

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> allPosts = postRepository.findAll();

        assertNotNull(allPosts);
        assertEquals(2, allPosts.size());
        assertEquals("Post 1", allPosts.get(0).getDescription());
        assertEquals("Post 2", allPosts.get(1).getDescription());
    }
    @Test
    void testDeleteById() {
        Post savedPost = postRepository.save(createSamplePost("Post to be deleted"));

        postRepository.deleteById(savedPost.getId());

        assertFalse(postRepository.existsById(savedPost.getId()));
    }

    @Test
    void testFindByUserId() {
        User user = new User();
        user.setUsername("testUser");
        userRepository.save(user);

        Post post1 = new Post();
        post1.setDescription("Post 1");
        post1.setUser(user);

        Post post2 = new Post();
        post2.setDescription("Post 2");
        post2.setUser(user);

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> userPosts = postRepository.findByUserId(user.getId());

        assertNotNull(userPosts);
        assertEquals(2, userPosts.size());
        assertTrue(userPosts.stream().allMatch(post -> post.getUser().equals(user)));
    }

    private Post createSamplePost(String description) {
        Post post = new Post();
        post.setDescription(description);
        return post;
    }
}
