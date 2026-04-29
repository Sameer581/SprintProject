package com.cg.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.PostDto;
import com.cg.dto.PostResponseDto;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public PostResponseDto createPost(PostDto dto) {

        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new NotAvailableException("User not found " + dto.getUserId()));

        Post post = new Post();
        post.setContent(dto.getContent());
        post.setUser(user);
        post.setTimestamp(new Timestamp(System.currentTimeMillis()));

        Post saved = postRepo.save(post);

        return mapToDto(saved);
    }

    @Override
    public PostResponseDto getPost(Long postId) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotAvailableException("Post not found " + postId));

        return mapToDto(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {

        return postRepo.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<PostResponseDto> getPostsByUser(Long userId) {

        return postRepo.findAll()
                .stream()
                .filter(p -> p.getUser().getUserId().equals(userId))
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public PostResponseDto updatePost(Long postId, PostDto dto) {

        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new NotAvailableException("Post not found " + postId));

        post.setContent(dto.getContent());

        Post updated = postRepo.save(post);

        return mapToDto(updated);
    }


    private PostResponseDto mapToDto(Post post) {

        PostResponseDto dto = new PostResponseDto();

        dto.setPostId(post.getPostId());
        dto.setContent(post.getContent());
        dto.setTimestamp(post.getTimestamp());

        dto.setUserId(post.getUser().getUserId());
        dto.setUsername(post.getUser().getUsername());

        return dto;
    }
}