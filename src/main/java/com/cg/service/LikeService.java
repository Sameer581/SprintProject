package com.cg.service;

import com.cg.dto.LikeDto;

public interface LikeService {

    LikeDto toggleLike(LikeDto likeDto);

    int countLikesByPost(Long postId);
    
    boolean hasUserLiked(Long userId, Long postId);
}