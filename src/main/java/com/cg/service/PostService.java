package com.cg.service;

import java.util.List;

import com.cg.dto.PostDto;
import com.cg.dto.PostResponseDto;

public interface PostService {

	    PostResponseDto createPost(PostDto dto);

	    PostResponseDto getPost(Long postId);

	    List<PostResponseDto> getAllPosts();

	    List<PostResponseDto> getPostsByUser(Long userId);

	    PostResponseDto updatePost(Long postId, PostDto dto);

	}
