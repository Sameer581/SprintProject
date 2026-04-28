package com.cg.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.dto.LikeDto;
import com.cg.entity.Like;
import com.cg.entity.Post;
import com.cg.entity.User;
import com.cg.repo.LikeRepo;
import com.cg.repo.PostRepo;
import com.cg.repo.UserRepo;

@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeRepo likeRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PostRepo postRepo;

    @Transactional
    @Override
    public LikeDto toggleLike(LikeDto likeDto) {

        User user = userRepo.findById(likeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepo.findById(likeDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        LikeDto response = new LikeDto();
        response.setUserId(user.getUserID());
        response.setPostId(post.getPostId());

        boolean alreadyLiked = likeRepo.existsByUserUserIDAndPostPostID(
                likeDto.getUserId(), likeDto.getPostId());

        if (alreadyLiked) {
            Like existingLike = likeRepo.findByUserUserIDAndPostPostID(
                    likeDto.getUserId(), likeDto.getPostId());

            likeRepo.delete(existingLike);

            response.setMessage("Post unliked successfully");
            response.setTotalLikes(likeRepo.countByPostPostID(post.getPostId()));
            return response;
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        Like savedLike = likeRepo.save(like);

        response.setLikeId(savedLike.getLikeId());
        response.setMessage("Post liked successfully");
        response.setTotalLikes(likeRepo.countByPostPostID(post.getPostId()));

        return response;
    }

    @Override
    public int countLikesByPost(Long postId) {
        return likeRepo.countByPostPostID(postId);
    }

    @Override
    public boolean hasUserLiked(Long userId, Long postId) {
        return likeRepo.existsByUserUserIDAndPostPostID(userId, postId);
    }

    @Override
    public List<LikeDto> getLikesByPost(Long postId) {
        List<Like> likes = likeRepo.findByPostPostID(postId);

        return likes.stream().map(like -> {
            LikeDto dto = new LikeDto();
            dto.setLikeId(like.getLikeId());
            dto.setUserId(like.getUser().getUserID());
            dto.setPostId(like.getPost().getPostId());
            dto.setMessage("Like fetched successfully");
            dto.setTotalLikes(likeRepo.countByPostPostID(postId));
            return dto;
        }).toList();
    }

    @Override
    public List<LikeDto> getLikesByUser(Long userId) {
        List<Like> likes = likeRepo.findByUserUserID(userId);

        return likes.stream().map(like -> {
            LikeDto dto = new LikeDto();
            dto.setLikeId(like.getLikeId());
            dto.setUserId(like.getUser().getUserID());
            dto.setPostId(like.getPost().getPostId());
            dto.setMessage("Like fetched successfully");
            dto.setTotalLikes(likeRepo.countByPostPostID(like.getPost().getPostId()));
            return dto;
        }).toList();
    }
}