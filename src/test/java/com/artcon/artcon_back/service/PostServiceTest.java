package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.MediaFile;
import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.PostRepository;
import com.artcon.artcon_back.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private InterestRepository interestRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private FileStorageService fileStorageService;
    @InjectMocks
    private PostService postService;

    @Test
    void testSubmitPostToDB() {
        User mockUser = new User(); // You can customize this according to your User class
        Interest mockInterest = new Interest(); // You can customize this according to your Interest class
        MultipartFile mockFile = Mockito.mock(MultipartFile.class);

        PostRequest postRequest = new PostRequest();
        postRequest.setUser_id(1);
        postRequest.setInterest_id(1L);
        postRequest.setDescription("Test description");
        postRequest.setMediafiles(Optional.of(new MultipartFile[]{mockFile}));

        Mockito.when(userRepository.findUserById(Mockito.anyInt())).thenReturn(Optional.of(mockUser));

        Mockito.when(interestRepository.findInterestById(Mockito.anyLong())).thenReturn(Optional.of(mockInterest));

        try {
            Mockito.when(fileStorageService.saveFile(Mockito.any(MultipartFile.class))).thenReturn("mocked_file_url");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Mockito.when(postRepository.save(Mockito.any(Post.class))).thenReturn(new Post()); // You can customize this according to your Post class

        Post result = postService.submitPostToDB(postRequest);

        Mockito.verify(userRepository, Mockito.times(1)).findUserById(Mockito.anyInt());
        Mockito.verify(interestRepository, Mockito.times(1)).findInterestById(Mockito.anyLong());
        try {
            Mockito.verify(fileStorageService, Mockito.times(1)).saveFile(Mockito.any(MultipartFile.class));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Mockito.verify(postRepository, Mockito.times(1)).save(Mockito.any(Post.class));
    }

    @Test
    void testGetPost() {

        Integer postId = 1;
        Post expectedPost = new Post();
        expectedPost.setId(postId);
        expectedPost.setDescription("Test post");

        when(postRepository.findById(postId)).thenReturn(Optional.of(expectedPost));

        Post result = postService.getPost(postId);

        assertNotNull(result);
        assertEquals(expectedPost, result);
    }

    @Test
    void testGetPostsByUserId() {
        Integer userId = 1;
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1);
        post1.setDescription("Post 1");
        expectedPosts.add(post1);

        when(postRepository.findByUserId(userId)).thenReturn(expectedPosts);

        List<Post> result = postService.getPostsByUserId(userId);

        assertNotNull(result);
        assertEquals(expectedPosts, result);
    }

    @Test
    void testDeletePost() {
        Integer postId = 1;
        assertDoesNotThrow(() -> postService.deletePost(postId));

        verify(postRepository, times(1)).deleteById(postId);
    }

    @Test
    void testFindAllPosts() {
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1);
        post1.setDescription("Post 1");
        expectedPosts.add(post1);

        when(postRepository.findAll()).thenReturn(expectedPosts);

        List<Post> result = postService.findAllPosts();

        assertNotNull(result);
        assertEquals(expectedPosts, result);
    }

    @Test
    void testSearchPosts() {
        String query = "test";
        List<Post> expectedPosts = new ArrayList<>();
        Post post1 = new Post();
        post1.setId(1);
        post1.setDescription("Test post 1");
        expectedPosts.add(post1);

        when(postRepository.searchPosts(query)).thenReturn(expectedPosts);

        List<Post> result = postService.searchPosts(query);

        assertNotNull(result);
        assertEquals(expectedPosts, result);
    }
}
