package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.service.PortfolioPostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final PortfolioPostService portfolioPostService;
    //create a portfolio post
    @PostMapping
    public ResponseEntity<Void> createPortfolioPost(
            @ModelAttribute PortfolioPostRequest portfolioPostRequest) {
        try {
            portfolioPostService.createPortfolioPost(portfolioPostRequest);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    //get portfolio post by id
    @GetMapping("/{portfolioPostId}")
    public ResponseEntity<PortfolioPost> getPortfolioPost(@PathVariable Integer portfolioPostId) {
        try {
            PortfolioPost portfolioPost = portfolioPostService.getPortfolioPost(portfolioPostId);
            return ResponseEntity.ok(portfolioPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //edit portfolio post
    @PutMapping("/{portfolioPostId}")
    public ResponseEntity<Void> updatePortfolioPost(
            @PathVariable Integer portfolioPostId,
            @RequestParam(required = false) MultipartFile media,
            @ModelAttribute PortfolioPostRequest updatePortfolioPostRequest) {
        try {
            updatePortfolioPostRequest.setMedia(media);
            portfolioPostService.updatePortfolioPost(portfolioPostId, updatePortfolioPostRequest);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // delete portfolio post by id
    @DeleteMapping("/{portfolioPostId}")
    public ResponseEntity<Void> deletePortfolioPost(@PathVariable Integer portfolioPostId) {
        try {
            portfolioPostService.deletePortfolioPost(portfolioPostId);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
