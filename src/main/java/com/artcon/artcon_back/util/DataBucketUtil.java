package com.artcon.artcon_back.util;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;

import static com.google.common.io.Files.getFileExtension;

@Component
public class DataBucketUtil {
    @Value("${gcpConfigFile}")
    private String gcpConfigFile;
    @Value("${gcpProjectId}")
    private String gcpProjectId;
    @Value("${gcpBucketId}")
    private String gcpBucketId;
    @Value("${gcpDirName}")
    private String gcpDirectoryName;

    public String uploadFile(MultipartFile multipartFile, String fileName, String contentType) throws Exception {

        try{

            System.out.println("Start file uploading process on GCS");
            if (gcpConfigFile == null || gcpProjectId == null || gcpBucketId == null || gcpDirectoryName == null) {
                throw new IllegalArgumentException("GCP configuration parameters cannot be null");
            }
            byte[] fileData = FileUtils.readFileToByteArray(convertFile(multipartFile));

            InputStream inputStream = new ClassPathResource(gcpConfigFile).getInputStream();

            StorageOptions options = StorageOptions.newBuilder().setProjectId(gcpProjectId)
                    .setCredentials(GoogleCredentials.fromStream(inputStream)).build();

            Storage storage = options.getService();
            Bucket bucket = storage.get(gcpBucketId,Storage.BucketGetOption.fields());

            RandomString id = new RandomString(6, ThreadLocalRandom.current());
            Blob blob = bucket.create(gcpDirectoryName + "/" + fileName + "-" + id.nextString() + checkFileExtension(fileName), fileData, contentType);

            if(blob != null){
                System.out.println("File successfully uploaded to GCS");
                return blob.getMediaLink();
            }

        }catch (Exception e){
            System.out.println("An error occurred while uploading data. Exception: " + e);
            throw new Exception("An error occurred while storing data to GCS");
        }
        throw new Exception("An error occurred while storing data to GCS");
    }

    private File convertFile(MultipartFile file) throws Exception {

        try{
            if(file.getOriginalFilename() == null){
                throw new Exception("Original file name is null");
            }
//            File convertedFile = new File(file.getOriginalFilename());
            File convertedFile = Files.createTempFile("uploadedFile", getFileExtension(file.getOriginalFilename())).toFile();

            FileOutputStream outputStream = new FileOutputStream(convertedFile);
            outputStream.write(file.getBytes());
            outputStream.close();
            System.out.println("Converting multipart file : {}" + convertedFile);
            return convertedFile;
        }catch (Exception e){
            throw new Exception("An error has occurred while converting the file");
        }
    }

    private String checkFileExtension(String fileName) throws Exception {
        if(fileName != null && fileName.contains(".")){
            String[] extensionList = {".png", ".jpeg", "jpg", ".pdf", ".doc", ".mp4"};

            for(String extension: extensionList) {
                if (fileName.endsWith(extension)) {
                    System.out.println("Accepted file type : {}" + extension);
                    return extension;
                }
            }
        }
        System.out.println("Not a permitted file type");
        throw new Exception("Not a permitted file type");
    }
}