package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Interest;
import com.artcon.artcon_back.model.Post;
import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.repository.InterestRepository;
import com.artcon.artcon_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PostService {

    @Autowired
    PostRepository postRepo;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    public Post submitPostToDB(PostRequest postRequest){

        Date date = new Date();
        //long time=date.getTime();
        //Timestamp dateTime=new Timestamp(time);
        //LocalDateTime dateTime = LocalDateTime.now();
        //Timestamp timestamp = Timestamp.valueOf(dateTime);


        long post_user_id = postRequest.getUser_id();
        User user = userRepository.findUserById(post_user_id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println(user.getType());

        long post_interest_id = postRequest.getInterest_id();
        Interest interest = interestRepository.findInterestById(post_interest_id)
                .orElseThrow(() -> new RuntimeException("Interest not found"));

        String description = postRequest.getDescription();
        //media
        String fileUrl = null;// pour le moment

        //mediaend
        Post posttobesave = new Post();

        posttobesave.setDescription(description);
        posttobesave.setPostImgURL(fileUrl);
        posttobesave.setDate(date);
        posttobesave.setLikes(0);
        posttobesave.setInterest(interest);
        posttobesave.setUser(user);


        Post result= postRepo.save(posttobesave);
        System.out.println("Post saved successfully:"+ result);
        return result;

    }


    public Post getPost(Integer post_id){
        System.out.println("Start get serv");
        Post result=postRepo.findPostById(post_id);
        User user = Post.getUser();
        result.setUser_id(user.getId());
        Interest interest = Post.getInterest();

        Long interestidLong = interest.getId();
        long longValue = interestidLong.longValue();
        int interestInt = (int) longValue;
        result.setInterest_id(interestInt);
        System.out.println("end get serv");

        //

        return result;
    }

    public void deletePost(Integer post_id){
        postRepo.deleteById(post_id);

    }



}
