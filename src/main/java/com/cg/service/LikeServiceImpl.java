package com.cg.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    @Override
    @Transactional
    public LikeDto toggleLike(LikeDto likeDto) {
        User user = userRepo.findById(likeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepo.findById(likeDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        LikeDto response = new LikeDto();
        response.setUserId(user.getUserId());
        response.setPostId(post.getPostId());

        boolean alreadyLiked = likeRepo.existsByUserUserIdAndPostPostId(
                likeDto.getUserId(), likeDto.getPostId());

        if (alreadyLiked) {
            Like existingLike = likeRepo.findByUserUserIdAndPostPostId(
                    likeDto.getUserId(), likeDto.getPostId());

            likeRepo.delete(existingLike);

            response.setMessage("Post unliked successfully");
            response.setTotalLikes(likeRepo.countByPostPostId(post.getPostId()));
            return response;
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.setTimestamp(new Timestamp(new Date().getTime()));

        Like savedLike = likeRepo.save(like);

        response.setLikeId(savedLike.getLikeId());
        response.setTimestamp(savedLike.getTimestamp());
        response.setMessage("Post liked successfully");
        response.setTotalLikes(likeRepo.countByPostPostId(post.getPostId()));

        return response;
    }

    @Override
    public int countLikesByPost(Long postId) {
        return likeRepo.countByPostPostId(postId);
    }

    @Override
    public boolean hasUserLiked(Long userId, Long postId) {
        return likeRepo.existsByUserUserIdAndPostPostId(userId, postId);
    }

    @Override
    public List<LikeDto> getLikesByPost(Long postId) {
        return likeRepo.findByPostPostId(postId).stream().map(this::convertToDto).toList();
    }

    @Override
    public List<LikeDto> getLikesByUser(Long userId) {
        return likeRepo.findByUserUserId(userId).stream().map(this::convertToDto).toList();
    }

    @Override
    public void removeLike(Long userId, Long postId) {
        likeRepo.deleteByUserUserIdAndPostPostId(userId, postId);
    }

    @Override
    public List<LikeDto> getRecentLikes() {
        return likeRepo.findAllByOrderByTimestampDesc().stream().map(this::convertToDto).toList();
    }

    @Override
    public List<LikeDto> getRecentLikesByCount(int count) {
        return likeRepo.findAllByOrderByTimestampDesc(PageRequest.of(0, count))
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public List<Object[]> getTopLikedPosts() {
        return likeRepo.findTopLikedPosts();
    }

    @Override
    public int countLikesByUser(Long userId) {
        return likeRepo.countByUserUserId(userId);
    }

    @Override
    public void clearLikesByPost(Long postId) {
        likeRepo.deleteByPostPostId(postId);
    }

    private LikeDto convertToDto(Like like) {
        LikeDto dto = new LikeDto();
        dto.setLikeId(like.getLikeId());
        dto.setUserId(like.getUser().getUserId());
        dto.setPostId(like.getPost().getPostId());
        dto.setTimestamp(like.getTimestamp());
        dto.setMessage("Like fetched successfully");
        dto.setTotalLikes(likeRepo.countByPostPostId(like.getPost().getPostId()));
        return dto;
    }
}