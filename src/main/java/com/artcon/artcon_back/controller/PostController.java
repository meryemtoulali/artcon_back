package com.artcon.artcon_back.controller;

import com.artcon.artcon_back.model.PostRequest;
import com.artcon.artcon_back.model.PostResponse;
import com.artcon.artcon_back.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.model.Post;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/add")
    public PostResponse submitPost( @RequestParam("user_id") Integer userId,
                                    @RequestParam("description") String description,
                                    @RequestParam("mediafiles") Optional<MultipartFile[]> mediafiles,
                                    @RequestParam("interest_id") Long interestId){

        PostRequest postRequest = new PostRequest(userId, description, mediafiles, interestId);
        Post postRes = postService.submitPostToDB(postRequest);
        Integer postId = postRes.getId();
        System.out.println("id post after save"+ postId);
        PostResponse result= new PostResponse();
        result.setSuccess(true);
        result.setMessage("Post added successfully");
        result.setPostId(postId);
        return result;
    }

    //get post by post id
    @GetMapping("/{post_id}")
    public ResponseEntity<Post> getPost(@PathVariable Integer post_id){
       /* System.out.println("Start get control");
        Post result = postService.getPost(post_id);
        System.out.println("result name"+ result.getDescription());
        System.out.println("end get control");
        return result;*/
        Post post = postService.getPost(post_id);
        return ResponseEntity.ok(post);
    }
    // get post by owner id
    @GetMapping("/owner/{user_id}")
    public ResponseEntity<List<Post>> getUserPosts(@PathVariable Integer user_id){
        List<Post> posts = postService.getPostsByUserId(user_id);
        System.out.println("get posts:" + posts.toString());
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
    @DeleteMapping("/delete/{post_id}")
    public PostResponse deletePost(@PathVariable Integer post_id){
        postService.deletePost(post_id);
        PostResponse result= new PostResponse();
        result.setSuccess(true);
        result.setMessage("Post deleted successfully");
        return result;
    }
    @GetMapping("/search")
    public ResponseEntity<List<Post>> searchPosts(@RequestParam("query") String query) {
        return ResponseEntity.ok(postService.searchPosts(query));
    }

}