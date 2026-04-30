package com.cg.service;

import com.cg.dto.LikeDto;
import java.util.List;

public interface LikeService {

    LikeDto toggleLike(LikeDto likeDto);

    int countLikesByPost(Long postId);

    boolean hasUserLiked(Long userId, Long postId);

    List<LikeDto> getLikesByPost(Long postId);

    List<LikeDto> getLikesByUser(Long userId);
}