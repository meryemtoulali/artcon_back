package com.artcon.artcon_back.service;

import com.artcon.artcon_back.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import com.artcon.artcon_back.repository.PostRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static java.security.Timestamp.*;

public class PostService {

    @Autowired
    PostRepository postRepo;

    public ArrayList<Post> submitPostToDB(Post postData){

        Date date=new Date();
        long time=date.getTime();
        Timestamp dateTime=new Timestamp(time);

        postData.setLikes(0);
        postData.setDateTime(dateTime);

        postRepo.save(postData);
        ArrayList<Post> result=retrivePostFromDB();
        return result;
    }


    public ArrayList<Post> retrivePostFromDB(){
        ArrayList<Post> result=postRepo.findAll();
        return result;
    }

    public void deletePostFromDB(int post_id){
        postRepo.deleteById(post_id);

    }



}
