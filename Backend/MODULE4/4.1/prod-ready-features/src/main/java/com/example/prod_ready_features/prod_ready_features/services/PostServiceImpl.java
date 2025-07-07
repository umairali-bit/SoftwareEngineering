package com.example.prod_ready_features.prod_ready_features.services;

import com.example.prod_ready_features.prod_ready_features.dtos.PostDTO;
import com.example.prod_ready_features.prod_ready_features.entities.PostEntity;
import com.example.prod_ready_features.prod_ready_features.exceptions.ResourceNotFoundException;
import com.example.prod_ready_features.prod_ready_features.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private PostRepository postRepository1;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postEntity -> modelMapper.map(postEntity, PostDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO postDTO) {

        PostEntity postEntity = modelMapper.map(postDTO, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Long postId) {
        PostEntity postEntity = postRepository
                .findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("The ID was not found "+ postId));

        return modelMapper.map(postEntity, PostDTO.class);
    }
    /*
    public PostDTO getPostById(Long postId){
    PostEntity postEntity = postRepository.findBy(id).
        orElseThrow(()-> new ResourceNotFoundException("Post not found"));
    return modelMapper.map(postEntity, PostDTO.class);
    }
     */
}
