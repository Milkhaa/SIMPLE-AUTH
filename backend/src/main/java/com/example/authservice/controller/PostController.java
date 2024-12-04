package com.example.authservice.controller;

import com.example.authservice.dto.PostDto;
import com.example.authservice.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/user")
    public ResponseEntity<List<PostDto>> getUserPosts(Authentication authentication) {
        String username = authentication.getName();
        System.out.println("Fetching posts for user: " + username);
        
        List<PostDto> posts = postService.getUserPosts(username);
        System.out.println("Found " + posts.size() + " posts");
        
        return ResponseEntity.ok(posts);
    }
} 