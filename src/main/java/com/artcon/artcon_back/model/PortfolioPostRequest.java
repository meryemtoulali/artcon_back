package com.artcon.artcon_back.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class PortfolioPostRequest {
    private Integer userId;
    private String title;
    private String caption;
    private MultipartFile media;
}

