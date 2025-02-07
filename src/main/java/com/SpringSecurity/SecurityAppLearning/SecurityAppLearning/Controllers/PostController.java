package com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Controllers;

import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Dto.PostDTO;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Entity.UserEntity;
import com.SpringSecurity.SecurityAppLearning.SecurityAppLearning.Service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor

public class PostController {

    private final PostService service;

    @GetMapping("/getPostById/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Long postId) {

        return ResponseEntity.ok(service.getPostById(postId));
    }

    @PostMapping("/createNewPost")
    public ResponseEntity<PostDTO> createNewPost(@RequestBody PostDTO newPost) {
        return ResponseEntity.ok(service.createNewPost(newPost));
    }

    @DeleteMapping("/deletePostById/{postId}")
    public ResponseEntity<String> deletePostById(@PathVariable Long postId) {
        service.deletePostById(postId);
        return ResponseEntity.ok("Post is deleted");
    }

    @GetMapping("/getAllPost")
    public ResponseEntity<List<PostDTO>> getAllPost() {
        return new ResponseEntity<>(service.getAllPost(), HttpStatus.OK);
    }
}
