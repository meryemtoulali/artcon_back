package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.PostResponse;
import com.artcon.artcon_back.model.User;
import com.artcon.artcon_back.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;
import java.util.List;


@RestController
@RequestMapping("/post")

public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add")
    public PostResponse submitPost( @RequestParam("user_id") Integer userId,
                                    @RequestParam("description") String description,
                                    @RequestParam("mediafiles") MultipartFile[] mediafiles,
                                    @RequestParam("interest_id") Long interestId){

        PostRequest postRequest = new PostRequest(userId, description, mediafiles, interestId);
        postService.submitPostToDB(postRequest);
        PostResponse result= new PostResponse();
        result.setSuccess(true);
        result.setMessage("Post added successfully");
        return result;
    }

//get post by post id
    @GetMapping("/{post_id}")
    public Post getPost(@PathVariable Integer post_id){
        System.out.println("Start get control");
        Post result = postService.getPost(post_id);
        System.out.println("end get control");
        return result;
    }
    @DeleteMapping("/delete/{post_id}")
    public PostResponse deletePost(@PathVariable Integer post_id){
        postService.deletePost(post_id);
        PostResponse result= new PostResponse();
        result.setSuccess(true);
        result.setMessage("Post deleted successfully");
        return result;

    }

    @GetMapping("/all")
    public ResponseEntity<List<Post>> getAllUser() {
        List<Post> posts = postService.findAllPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

}
