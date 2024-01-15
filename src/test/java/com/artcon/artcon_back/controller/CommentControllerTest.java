package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Comment;
import com.artcon.artcon_back.model.CommentRequest;
import com.artcon.artcon_back.repository.CommentRepository;
import com.artcon.artcon_back.service.CommentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentController commentController;

    private MockMvc mockMvc;

    @Test
    void comment_ReturnsComment() throws Exception {
        when(commentService.addComment(any(CommentRequest.class))).thenReturn(new Comment());

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        CommentRequest commentRequest = new CommentRequest();
        commentRequest.setUser_id(1);
        commentRequest.setPost_id(123);
        commentRequest.setContent("Test Comment");

        mockMvc.perform(MockMvcRequestBuilders.post("/comment")
                        .contentType("application/json")
                        .content("{\"user_id\": 1, \"post_id\": 123, \"content\": \"Test Comment\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getCommentsByPostId_ReturnsListOfComments() throws Exception {
        when(commentRepository.findByPost_Id(123)).thenReturn(Arrays.asList(new Comment(), new Comment()));

        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/comment/byPost/123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void deleteComment_ReturnsSuccessMessage() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/comment/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").value("Comment deleted successfully"));

        verify(commentRepository, times(1)).deleteById(1);
    }
}
