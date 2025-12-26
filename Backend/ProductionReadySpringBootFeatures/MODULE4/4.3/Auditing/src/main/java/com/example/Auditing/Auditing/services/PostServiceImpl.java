package com.example.Auditing.Auditing.services;

import com.example.Auditing.Auditing.dtos.PostDTO;
import com.example.Auditing.Auditing.entities.PostEntity;
import com.example.Auditing.Auditing.exceptions.ResourceNotFoundException;
import com.example.Auditing.Auditing.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

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
                .orElseThrow(() -> new ResourceNotFoundException("The ID:" + postId + " was not found "));

        return modelMapper.map(postEntity, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO inputPost, Long postId) {
        PostEntity olderPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("The ID:" + postId + " was not found "));
        inputPost.setId(postId);
        modelMapper.map(inputPost, olderPost);
        PostEntity savedPostEntity = postRepository.save(olderPost);
        return modelMapper.map(savedPostEntity, PostDTO.class);

    }
    /*
    public PostDTO getPostById(Long postId){
    PostEntity postEntity = postRepository.findBy(id).
        orElseThrow(()-> new ResourceNotFoundException("Post not found"));
    return modelMapper.map(postEntity, PostDTO.class);
    }
     */
}
