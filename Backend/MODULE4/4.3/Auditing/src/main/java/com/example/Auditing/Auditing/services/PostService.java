package com.example.Auditing.Auditing.services;

import com.example.Auditing.Auditing.dtos.PostDTO;

import java.util.List;


public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost (PostDTO postDTO);

    PostDTO getPostById(Long postId);
}
