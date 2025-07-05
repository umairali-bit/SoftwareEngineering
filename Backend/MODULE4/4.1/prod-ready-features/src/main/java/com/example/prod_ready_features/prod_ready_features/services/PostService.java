package com.example.prod_ready_features.prod_ready_features.services;

import com.example.prod_ready_features.prod_ready_features.dtos.PostDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface PostService {

    List<PostDTO> getAllPosts();

    PostDTO createNewPost (PostDTO postDTO);
}
