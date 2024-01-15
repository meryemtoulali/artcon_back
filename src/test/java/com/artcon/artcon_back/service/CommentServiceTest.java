package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.CommentRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostService postService;

    @Mock
    private CommentRepository commentRepository;

    @Test
    void testAddComment() {

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setUser_id(1);
        commentRequest.setPost_id(2);
        commentRequest.setContent("Test comment");

        User user = new User();
        user.setId(commentRequest.getUser_id());

        Post post = new Post();
        post.setId(commentRequest.getPost_id());
        post.setComments_count(0);

        Date currentDate = new Date();

        when(userRepository.findUserById(1)).thenReturn(Optional.of(user));

        when(postService.getPost(2)).thenReturn(post);

        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            Comment savedComment = invocation.getArgument(0);
            savedComment.setId(1);
            return savedComment;
        });

        Comment result = commentService.addComment(commentRequest);

        assertEquals(1, post.getComments_count());
        verify(postRepository, times(1)).save(post);

        assertNotNull(result);
        assertEquals(user, result.getUser());
        assertEquals(post, result.getPost());
        assertEquals(commentRequest.getContent(), result.getContent());
        assertNotNull(result.getDate());
    }
}
