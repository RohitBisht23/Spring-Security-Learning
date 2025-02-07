package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.PostDTO;

import java.util.List;

public interface PostService {

    PostDTO getPostById(Long id);
    List<PostDTO> getAllPost();
    void deletePostById(Long id);
    PostDTO createNewPost(PostDTO newPost);
}
