package com.artcon.artcon_back.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "\"mediafile\"")
public class MediaFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mediafile_id")
    private Integer Id;

    @Column(name = "mediafile_url")
    private String mediafile_url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private static Post post;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getMediafile_url() {
        return mediafile_url;
    }

    public void setMediafile_url(String mediafile_url) {
        this.mediafile_url = mediafile_url;
    }

    public static Post getPost() {
        return post;
    }

    public static void setPost(Post post) {
        MediaFile.post = post;
    }
}