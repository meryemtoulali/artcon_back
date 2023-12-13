package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.PostResponse;
import com.artcon.artcon_back.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/post")

public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add")
    public PostResponse submitPost(@RequestBody PostRequest postRequest){
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

}
