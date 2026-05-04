package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.PostDto;
import com.cg.dto.PostResponseDto;
import com.cg.service.PostService;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:4200")
public class PostController {

    @Autowired
    private PostService postService;

    // ✅ GET ALL POSTS
    @GetMapping
    public List<PostResponseDto> getAllPosts() {
        return postService.getAllPosts();
    }

    // ✅ GET SINGLE POST
    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // ✅ CREATE POST
    @PostMapping
    public PostResponseDto createPost(@RequestBody PostDto dto) {
        return postService.createPost(dto);
    }

    // ✅ UPDATE POST
    @PutMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostDto dto) {
        return postService.updatePost(id, dto);
    }

    // ✅ GET POSTS BY USER
    @GetMapping("/user/{userId}")
    public List<PostResponseDto> getPostsByUser(@PathVariable Long userId) {
        return postService.getPostsByUser(userId);
    }
}