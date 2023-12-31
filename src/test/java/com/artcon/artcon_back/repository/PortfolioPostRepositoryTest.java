package com.artcon.artcon_back.repository;
import com.artcon.artcon_back.model.PortfolioPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

public class PortfolioPostRepositoryTest {
    @Autowired
    private PortfolioPostRepository portfolioPostRepository;
    @Test
    void testSavePortfolioPost() {
        PortfolioPost portfolioPost = PortfolioPost.builder()
                .title("Test Title")
                .caption("Test Caption")
                .media("test_media_url")
                .build();

        PortfolioPost savedPost = portfolioPostRepository.save(portfolioPost);

        assertNotNull(savedPost.getId());
        assertEquals("Test Title", savedPost.getTitle());
        assertEquals("Test Caption", savedPost.getCaption());
        assertEquals("test_media_url", savedPost.getMedia());
    }

    @Test
    void testFindById() {
        PortfolioPost portfolioPost = PortfolioPost.builder()
                .title("Test Title")
                .caption("Test Caption")
                .media("test_media_url")
                .build();
        portfolioPostRepository.save(portfolioPost);

        Optional<PortfolioPost> foundPost = portfolioPostRepository.findById(portfolioPost.getId());

        assertTrue(foundPost.isPresent());
        assertEquals("Test Title", foundPost.get().getTitle());
        assertEquals("Test Caption", foundPost.get().getCaption());
        assertEquals("test_media_url", foundPost.get().getMedia());
    }

    @Test
    void testUpdatePortfolioPost() {
        PortfolioPost portfolioPost = PortfolioPost.builder()
                .title("Original Title")
                .caption("Original Caption")
                .media("original_media_url")
                .build();
        portfolioPostRepository.save(portfolioPost);

        portfolioPost.setTitle("Updated Title");
        portfolioPost.setCaption("Updated Caption");
        portfolioPost.setMedia("updated_media_url");
        PortfolioPost updatedPost = portfolioPostRepository.save(portfolioPost);

        assertEquals("Updated Title", updatedPost.getTitle());
        assertEquals("Updated Caption", updatedPost.getCaption());
        assertEquals("updated_media_url", updatedPost.getMedia());
    }


    @Test
    void testDeletePortfolioPost() {
        PortfolioPost portfolioPost = PortfolioPost.builder()
                .title("To Be Deleted")
                .caption("Delete Me")
                .media("delete_media_url")
                .build();
        portfolioPostRepository.save(portfolioPost);

        portfolioPostRepository.deleteById(portfolioPost.getId());

        Optional<PortfolioPost> deletedPost = portfolioPostRepository.findById(portfolioPost.getId());

        assertTrue(deletedPost.isEmpty());
    }


}
