package com.artcon.artcon_back.repository;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;

    @Test
    void testSaveComment() {
        Comment comment = new Comment();
        comment.setContent("Test Comment");

        Comment savedComment = commentRepository.save(comment);

        assertNotNull(savedComment.getId());
        assertEquals("Test Comment", savedComment.getContent());
    }

    @Test
    void testFindByPost_Id() {
        // Assuming you have a Post with ID 1 in your database
        // You may need to save a Post in the setup or use a Post from a real database
        Post post = new Post();
        post.setId(1);

        postRepository.save(post);

        Comment comment1 = new Comment();
        comment1.setContent("Comment 1");
        comment1.setPost(post);

        Comment comment2 = new Comment();
        comment2.setContent("Comment 2");
        comment2.setPost(post);

        commentRepository.save(comment1);
        commentRepository.save(comment2);

        List<Comment> comments = commentRepository.findByPost_Id(1);

        assertEquals(2, comments.size());
    }
}
