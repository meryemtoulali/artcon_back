package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.LikeRequest;
import com.artcon.artcon_back.service.LikeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
public class LikeControllerTest {

    @Mock
    private LikeService likeService;

    @InjectMocks
    private LikeController likeController;

    private MockMvc mockMvc;

    @Test
    void likePost_ReturnsLikeRes() throws Exception {
        doNothing().when(likeService).likePost(anyInt(), anyInt());

        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();

        LikeRequest likeRequest = new LikeRequest();
        likeRequest.setUser_id(1);
        likeRequest.setPost_id(123);

        mockMvc.perform(MockMvcRequestBuilders.post("/like")
                        .contentType("application/json")
                        .content("{\"user_id\": 1, \"post_id\": 123}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Post liked successfully"));
    }

    @Test
    void dislikePost_ReturnsLikeRes() throws Exception {
        doNothing().when(likeService).dislikePost(anyInt(), anyInt());

        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();

        LikeRequest likeRequest = new LikeRequest();
        likeRequest.setUser_id(1);
        likeRequest.setPost_id(123);

        mockMvc.perform(MockMvcRequestBuilders.post("/dislike")
                        .contentType("application/json")
                        .content("{\"user_id\": 1, \"post_id\": 123}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Post disliked successfully"));
    }

    @Test
    void hasUserLikedPost_ReturnsBoolean() throws Exception {
        when(likeService.hasUserLikedPost(anyInt(), anyInt())).thenReturn(true);

        mockMvc = MockMvcBuilders.standaloneSetup(likeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/hasUserLikedPost")
                        .param("userId", "1")
                        .param("postId", "123"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").value(true));
    }
}
