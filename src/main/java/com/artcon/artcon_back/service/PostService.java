package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
@Component
public class PostService {

    @Autowired
    PostRepository postRepo;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private FileStorageService fileStorageService;

    @Transactional
    public Post submitPostToDB(PostRequest postRequest){

        Date date = new Date();
        Integer post_user_id = postRequest.getUser_id();
        User user = userRepository.findUserById(post_user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long post_interest_id = postRequest.getInterest_id();
        Interest interest = interestRepository.findInterestById(post_interest_id)
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        String description = postRequest.getDescription();

        //media
        Optional<MultipartFile[]> files = postRequest.getMediafiles();

        List<MediaFile> mediaFiles;
        // for each file save it in cloud and then save the file_url in
        // a mediaFile and then save the List of media file(setmediafiles
        // in post)
        // Check if files are not empty
        //System.out.println("Files length: " + (files != null ? files.length : 0) + "file name : "+ files[0].getOriginalFilename());
        //if (files != null && files.length > 0) {
        System.out.println("files check ; " + files.isPresent());
        //System.out.println("file length : " + files.get().length);
        if (files.isPresent()) {
            MultipartFile[] Files = files.get();
            mediaFiles = new ArrayList<>();
            Arrays.asList(Files).stream().forEach(file -> {
            try {
                MediaFile mediaFile = new MediaFile();
                String fileUrl = null;
                System.out.println("about to upload");
                System.out.println("file name: " + file.getOriginalFilename());
                fileUrl = fileStorageService.saveFile(file);
                System.out.println("Uploaded file");
                mediaFile.setMediafile_url(fileUrl);
                mediaFiles.add(mediaFile);
            } catch (Exception e) {
                throw new RuntimeException("Error while saving media file", e);
            }
        });

        } else {
            // No files, set mediaFiles to null
            mediaFiles = null;
        }

        Post postToSave = new Post();

        postToSave.setDescription(description);
        postToSave.setUser(user);
        System.out.println("this is user i inserted : "+ user.getUsername());
        postToSave.setInterest(interest);
        System.out.println("this the interest i inserted : "+ interest.getInterest_name());

        postToSave.setMediaFiles(mediaFiles);
        postToSave.setDate(date);
        postToSave.setLikes(0);

        System.out.println("now we set user hahahahahaha: " + user.getUsername());

        Post result= postRepo.save(postToSave);
        System.out.println("Post saved successfully:"+ result);
        return result;
    }

    public Post getPost(Integer post_id){

        return postRepo.findById(post_id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + post_id));
    }

    public List<Post> getPostsByUserId(Integer user_id) {
        List<Post> posts = postRepo.findByUserId(user_id);

        return posts;
    }

    public void deletePost(Integer post_id){
        postRepo.deleteById(post_id);

    }


    public List<Post> findAllPosts() {
        return postRepo.findAll();
    }

    private static Integer longToInt(long longValue) {
        if (longValue >= Integer.MIN_VALUE && longValue <= Integer.MAX_VALUE) {
            return (int) longValue;
        } else {
            // Handle the case where the long value is outside the range of Integer
            throw new IllegalArgumentException("Long value is out of the range of Integer");
        }
    }

    public List<Post> searchPosts(String query){
        List<Post> posts = postRepo.searchPosts(query);
        return posts;
    }

}