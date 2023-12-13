package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.PortfolioPost;
import com.artcon.artcon_back.model.PortfolioPostRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.PortfolioPostRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PortfolioPostService {

    @Autowired
    private UserService userService;
    @Autowired
    private PortfolioPostRepository portfolioPostRepository;
    @Autowired
    private FileStorageService fileStorageService;

    public void createPortfolioPost(PortfolioPostRequest portfolioPostRequest) {
        User user = userService.findUserById(portfolioPostRequest.getUserId());
        System.out.println(user.getType());
//        if (!user.getType().equals("ARTIST")) {
//            throw new IllegalArgumentException("Only artists can create portfolio posts");
//        }

//
//        PortfolioPost portfolioPost = new PortfolioPost();
//        portfolioPost.setDate(portfolioPostRequest.getDate());
//        portfolioPost.setTitle(portfolioPostRequest.getTitle());

        // Save the file and get the file URL
        String fileUrl = null;
        System.out.println("about to upload");

        try {
            fileUrl = fileStorageService.saveFile(portfolioPostRequest.getMedia());

            System.out.println("Uploaded file");

        } catch (Exception e) {
            throw new RuntimeException("Error while saving media file", e);
        }
        Date currentDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
        System.out.println("Created date");

        PortfolioPost portfolioPost = PortfolioPost.builder()
                .user(user)
                .date(currentDate)
                .title(portfolioPostRequest.getTitle())
                .caption(portfolioPostRequest.getCaption())
                .media(fileUrl)
                .build();
        System.out.println("Built portfoliopost");

        user.addPortfolioPost(portfolioPost); //implicitly sets the user for portfolioPost
        System.out.println("Added portfoliopost to user");

//        userService.save(user);
        portfolioPostRepository.save(portfolioPost);
        System.out.println("Saved portfoliopost");

    }

    public PortfolioPost getPortfolioPost(Integer portfolioPostId) {
        PortfolioPost portfolioPost = portfolioPostRepository.findById(portfolioPostId)
                .orElseThrow(() -> new EntityNotFoundException("Portfolio post not found"));

        // Set user information
        User user = portfolioPost.getUser();
        portfolioPost.setUserId(user.getId());
        portfolioPost.setUsername(user.getUsername());
        portfolioPost.setFirstname(user.getFirstname());
        portfolioPost.setLastname(user.getLastname());
        return portfolioPost;
    }

//    public List<PortfolioPost> getPortfolioPostsByUser(Integer userId) {
//        User user = userService.findUserById(userId);
//        return portfolioPostRepository.findByUser(user);
//    }

    public void updatePortfolioPost(Integer portfolioPostId, PortfolioPostRequest updatePortfolioPostRequest) {
        PortfolioPost portfolioPost = getPortfolioPost(portfolioPostId);
//
//        if (updatePortfolioPostRequest.getDate() != null) {
//            portfolioPost.setDate(updatePortfolioPostRequest.getDate());
//        }

        if (updatePortfolioPostRequest.getTitle() != null) {
            portfolioPost.setTitle(updatePortfolioPostRequest.getTitle());
        }

        if (updatePortfolioPostRequest.getCaption() != null) {
            portfolioPost.setCaption(updatePortfolioPostRequest.getCaption());
        }

        if (updatePortfolioPostRequest.getMedia() != null) {
            // Save the updated media file and get the file URL
            try {
                String updatedMediaUrl = fileStorageService.saveFile(updatePortfolioPostRequest.getMedia());
                portfolioPost.setMedia(updatedMediaUrl);
            } catch (Exception e) {
                throw new RuntimeException("Error occurred while updating media");
            }
        }

        portfolioPostRepository.save(portfolioPost);
    }

    public void deletePortfolioPost(Integer portfolioPostId) {
        PortfolioPost portfolioPost = getPortfolioPost(portfolioPostId);
        portfolioPostRepository.delete(portfolioPost);
    }
}
