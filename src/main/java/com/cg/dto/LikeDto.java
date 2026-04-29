package com.cg.dto;

import java.sql.Timestamp;

import jakarta.validation.constraints.NotNull;



public class LikeDto {

    private Long likeId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Post ID is required")
    private Long postId;

    private Timestamp timestamp;
    private String message;
    private Integer totalLikes;

    public LikeDto() {
    }

    public LikeDto(Long likeId, Long userId, Long postId, Timestamp timestamp, String message, Integer totalLikes) {
        this.likeId = likeId;
        this.userId = userId;
        this.postId = postId;
        this.timestamp = timestamp;
        this.message = message;
        this.totalLikes = totalLikes;
    }

    public Long getLikeId() {
        return likeId;
    }

    public void setLikeId(Long likeId) {
        this.likeId = likeId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(Integer totalLikes) {
        this.totalLikes = totalLikes;
    }
}