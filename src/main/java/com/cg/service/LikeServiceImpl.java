package com.cg.service;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public LikeDto toggleLike(LikeDto likeDto) {

        User user = userRepo.findById(likeDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = postRepo.findById(likeDto.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        LikeDto response = new LikeDto();
        response.setUserId(user.getUserID());
        response.setPostId(post.getPostID());


        boolean alreadyLiked = likeRepo.existsByUserUserIdAndPostPostId(
                likeDto.getUserId(), likeDto.getPostId());


        if (alreadyLiked) {
            likeRepo.deleteByUserUserIdAndPostPostId(
                    likeDto.getUserId(), likeDto.getPostId());

            response.setMessage("Post unliked successfully");
            response.setTotalLikes(likeRepo.countByPostPostId(post.getPostID()));
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
        response.setTotalLikes(likeRepo.countByPostPostId(post.getPostID()));

        return response;
    }

    @Override
    public int countLikesByPost(Long postId) {
        return likeRepo.countByPostPostId(postId);
    }
}