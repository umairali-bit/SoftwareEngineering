package com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.utils;


import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.dtos.PostDTO;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.entities.UserEntity;
import com.example.ThridPartyAPICallsWithRestClient.ThridPartyAPICallsWithRestClient.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSecurity {

    private final PostService postService;

   public boolean isOwnerOfPost(Long postId) {
        //getting the current user
        UserEntity  user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDTO  postDTO = postService.getPostById(postId);

        return postDTO.getAuthor().getId().equals(user.getId());
    }


}
