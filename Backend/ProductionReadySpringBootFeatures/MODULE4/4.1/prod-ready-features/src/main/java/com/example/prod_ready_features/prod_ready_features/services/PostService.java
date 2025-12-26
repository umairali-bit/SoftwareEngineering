package com.example.prod_ready_features.prod_ready_features.services;

import com.example.prod_ready_features.prod_ready_features.dtos.PostDTO;

import java.util.List;
import java.util.Optional;


public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost (PostDTO postDTO);

    PostDTO getPostById(Long postId);
}
