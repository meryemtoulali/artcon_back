package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.*;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.repository.PostRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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


    public Post submitPostToDB(PostRequest postRequest){

        Date date = new Date();
        long post_user_id = postRequest.getUser_id();
        User user = userRepository.findUserById(post_user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        long post_interest_id = postRequest.getInterest_id();
        Interest interest = interestRepository.findInterestById(post_interest_id)
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        String description = postRequest.getDescription();

        //media
        MultipartFile[] files = postRequest.getMediafiles();

        List<MediaFile> mediaFiles = new ArrayList<>();
        // for each file save it in cloud and then save the file_url in
        // a mediaFile and then save the List of media file(setmediafiles
        // in post)
        Arrays.asList(files).stream().forEach(file -> {
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

        Post postToSave = new Post();

        postToSave.setDescription(description);
        postToSave.setInterest(interest);
        System.out.println("this the interest i inserted : "+ interest.getInterest_name());
        postToSave.setUser(user);
        System.out.println("this is user i inserted : "+ user.getUsername());
        postToSave.setMediaFiles(mediaFiles);
        postToSave.setDate(date);
        postToSave.setLikes(0);

        System.out.println("now we set user hahahahahaha: " + user.getUsername());

        Post result= postRepo.save(postToSave);
        System.out.println("Post saved successfully:"+ result);
        return result;

    }

    public Post getPost(Integer post_id){
        System.out.println("Start get serv");
        Post result=postRepo.findPostById(post_id);
       // User user = Post.getUser();
       // result.setUser_id(user.getId());

        Interest interest = Post.getInterest();
        Long interestidLong = interest.getId();
        //long longValue = interestidLong.longValue();
        //int interestInt = (int) longValue;
        //result.setInterest_id(interestInt);
        System.out.println("end get serv");

        //

        return result;
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
}
