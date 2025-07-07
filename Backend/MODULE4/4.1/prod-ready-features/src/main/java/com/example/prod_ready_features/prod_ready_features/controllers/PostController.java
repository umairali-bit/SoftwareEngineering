package com.example.prod_ready_features.prod_ready_features.controllers;


import com.example.prod_ready_features.prod_ready_features.dtos.PostDTO;
import com.example.prod_ready_features.prod_ready_features.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/{postId}")
    public Optional<PostDTO> getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }




    @PostMapping
    public PostDTO createNewPost(@RequestBody PostDTO inputPost) {
        return postService.createNewPost(inputPost);
    }




}


