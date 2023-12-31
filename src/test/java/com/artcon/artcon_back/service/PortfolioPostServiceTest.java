package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.PortfolioPost;
import com.artcon.artcon_back.model.PortfolioPostRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.PortfolioPostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.ArgumentMatchers.any;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PortfolioPostServiceTest {
    @InjectMocks
    private PortfolioPostService portfolioPostService;

    @Mock
    private UserService userService;

    @Mock
    private PortfolioPostRepository portfolioPostRepository;

    @Mock
    private FileStorageService fileStorageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreatePortfolioPost() {
        PortfolioPostRequest portfolioPostRequest = new PortfolioPostRequest();
        portfolioPostRequest.setUserId(1);
        portfolioPostRequest.setTitle("Test Title");
        portfolioPostRequest.setCaption("Test Caption");

        User user = new User();
        user.setId(1);
        user.setType("ARTIST");

        when(userService.findUserById(1)).thenReturn(user);
        try {
            when(fileStorageService.saveFile(any(MultipartFile.class))).thenReturn("fileUrl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Test
        assertDoesNotThrow(() -> portfolioPostService.createPortfolioPost(portfolioPostRequest));
    }

    @Test
    void testGetPortfolioPost() {
        // Mock data
        Integer portfolioPostId = 1;
        PortfolioPost portfolioPost = new PortfolioPost();
        portfolioPost.setId(portfolioPostId);
        when(portfolioPostRepository.findById(portfolioPostId)).thenReturn(Optional.of(portfolioPost));

        // Test
        PortfolioPost result = portfolioPostService.getPortfolioPost(portfolioPostId);

        // Verify
        assertNotNull(result);
        assertEquals(portfolioPost, result);
    }

    @Test
    void testGetPortfolioPostNotFound() {
        // Mock data
        Integer portfolioPostId = 1;
        when(portfolioPostRepository.findById(portfolioPostId)).thenReturn(Optional.empty());

        // Test
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            portfolioPostService.getPortfolioPost(portfolioPostId);
        });

        assertEquals("Portfolio post not found", exception.getMessage());
    }


    @Test
    void testUpdatePortfolioPost() {
        // Mock data
        Integer portfolioPostId = 1;
        PortfolioPostRequest updatePortfolioPostRequest = new PortfolioPostRequest();
        updatePortfolioPostRequest.setTitle("Updated Title");
        updatePortfolioPostRequest.setCaption("Updated Caption");

        PortfolioPost portfolioPost = new PortfolioPost();
        portfolioPost.setId(portfolioPostId);
        when(portfolioPostRepository.findById(portfolioPostId)).thenReturn(Optional.of(portfolioPost));
        try {
            when(fileStorageService.saveFile(any(MultipartFile.class))).thenReturn("updatedFileUrl");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Test
        assertDoesNotThrow(() -> portfolioPostService.updatePortfolioPost(portfolioPostId, updatePortfolioPostRequest));

        // Verify
        assertEquals(updatePortfolioPostRequest.getTitle(), portfolioPost.getTitle());
        assertEquals(updatePortfolioPostRequest.getCaption(), portfolioPost.getCaption());
    }

    @Test
    void testDeletePortfolioPost() {
        // Mock data
        Integer portfolioPostId = 1;
        PortfolioPost portfolioPost = new PortfolioPost();
        portfolioPost.setId(portfolioPostId);
        when(portfolioPostRepository.findById(portfolioPostId)).thenReturn(Optional.of(portfolioPost));

        // Test
        assertDoesNotThrow(() -> portfolioPostService.deletePortfolioPost(portfolioPostId));
    }

}
