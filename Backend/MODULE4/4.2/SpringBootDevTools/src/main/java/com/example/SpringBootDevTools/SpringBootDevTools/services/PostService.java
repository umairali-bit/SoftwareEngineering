package com.example.SpringBootDevTools.SpringBootDevTools.services;

import com.example.SpringBootDevTools.SpringBootDevTools.dtos.PostDTO;

import java.util.List;


public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost (PostDTO postDTO);

    PostDTO getPostById(Long postId);
}
