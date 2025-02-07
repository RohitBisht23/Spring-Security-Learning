package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.Impl;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.PostDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.PostEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.UserEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Exceptions.ResourceNotFoundException;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Repository.PostRepository;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.PostService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public PostDTO getPostById(Long id) {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User {}", user);
        PostEntity post= repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The post not found"));
        return modelMapper.map(post, PostDTO.class);
    }

    @Override
    public List<PostDTO> getAllPost() {
        return repository.findAll().stream().map(post-> modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }

    @Override
    public PostDTO createNewPost(PostDTO newPost) {
        PostEntity post = modelMapper.map(newPost, PostEntity.class);
        PostEntity savedPost = repository.save(post);
        return modelMapper.map(savedPost, PostDTO.class);
    }

    @Override
    public void deletePostById(Long id) {
        PostEntity post= repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The post not found"));
        repository.deleteById(id);
        return;
    }
}