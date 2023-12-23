package com.artcon.artcon_back.service;

import com.artcon.artcon_back.util.DataBucketUtil;
import com.google.cloud.storage.Storage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
@Service
@Transactional
@RequiredArgsConstructor
public class FileStorageService{

    @Autowired
    private DataBucketUtil dataBucketUtil;

    public String saveFile(MultipartFile file) throws Exception {
        // Implement logic to save the file to the storage solution (e.g., Google Cloud Storage)
        // Return the file URL or path
        String originalFileName = file.getOriginalFilename();
        if(originalFileName == null){
            throw new BadRequestException("Original filename is null.");
        }
//        Path path = new File(originalFileName).toPath();
        try {
            String contentType = file.getContentType();
            String fileUrl = dataBucketUtil.uploadFile(file, originalFileName, contentType);

            if (fileUrl != null) {
                System.out.println("File uploaded successfully, file url: " + fileUrl);
                return fileUrl;
            }
        } catch (Exception e) {
            System.out.println("Error occurred while uploading. Error: " + e);
            throw new Exception("Error occurred while uploading");
        }
        return "";
    }
}