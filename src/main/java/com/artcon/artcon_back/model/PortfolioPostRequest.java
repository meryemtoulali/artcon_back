package com.artcon.artcon_back.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class PortfolioPostRequest {
    private Integer userId;
    private String title;
    private MultipartFile media;
}

