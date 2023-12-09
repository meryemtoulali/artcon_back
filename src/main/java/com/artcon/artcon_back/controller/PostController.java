package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.model.Post;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;


@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add")
    public void submitPost(@RequestBody Post body){
        postService.submitPostToDB(body);

    }

    @GetMapping("/getPost")
    public ArrayList<Post> retrieveAllPost(){
        // need to add some changes and ADD THIS ONE /{postId}
        ArrayList<Post> result=postService.retrivePostFromDB();
        result.sort((e1, e2) -> e2.getDateTime().compareTo(e1.getDateTime()));
        return result;
    }
    @DeleteMapping("/delete/{postId}")
    public void deleteParticularPost(@PathVariable("postId") Integer post_id){
        postService.deletePostFromDB(post_id);

    }





}
