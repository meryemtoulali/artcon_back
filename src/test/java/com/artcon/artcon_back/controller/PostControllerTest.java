package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.PostResponse;
import com.artcon.artcon_back.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class PostControllerTest {
    @Mock
    private PostService postService;

    @InjectMocks
    private PostController postController;

    private MockMvc mockMvc;

    @Test
    void submitPost_ReturnsPostResponse() throws Exception {
        when(postService.submitPostToDB(any(PostRequest.class))).thenReturn(new Post());

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        MockMultipartFile[] files = {new MockMultipartFile("mediafiles", "file.txt", MediaType.TEXT_PLAIN_VALUE, "file data".getBytes())};

        mockMvc.perform(MockMvcRequestBuilders.multipart("/post/add")
                        .file(files[0])
                        .param("user_id", "1")
                        .param("description", "Test Description")
                        .param("interest_id", "1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Post added successfully"));
    }

    @Test
    void getPost_ReturnsPost() throws Exception {
        when(postService.getPost(1)).thenReturn(new Post());

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/post/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUserPosts_ReturnsListOfPosts() throws Exception {
        when(postService.getPostsByUserId(1)).thenReturn(Arrays.asList(new Post(), new Post()));

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/post/owner/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

    @Test
    void getLikeCount_ReturnsLikeCount() throws Exception {

        Post mockPost = new Post();
        mockPost.setLikes(5);

        when(postService.getPost(anyInt())).thenReturn(mockPost);

        ResponseEntity<Integer> response = postController.getLikeCount(1);

        verify(postService).getPost(1);

        assertEquals(5, response.getBody());
    }

    @Test
    void deletePost_ReturnsPostResponse() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.delete("/post/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Post deleted successfully"));

        verify(postService, times(1)).deletePost(1);
    }

    @Test
    void searchPosts_ReturnsListOfPosts() throws Exception {
        when(postService.searchPosts("test")).thenReturn(Arrays.asList(new Post(), new Post()));

        mockMvc = MockMvcBuilders.standaloneSetup(postController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/post/search").param("query", "test"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2));
    }

}
