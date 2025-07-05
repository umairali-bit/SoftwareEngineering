package com.example.prod_ready_features.prod_ready_features.services;

import com.example.prod_ready_features.prod_ready_features.dtos.PostDTO;
import com.example.prod_ready_features.prod_ready_features.repositories.PostRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;




    @Override
    public List<PostDTO> getAllPosts() {
        return null;
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {
        return null;
    }
}
