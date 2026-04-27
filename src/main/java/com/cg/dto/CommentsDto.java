package com.cg.dto;

import java.time.LocalDateTime;

public class CommentsDto {

    private Integer commentID;
    private Integer postID;
    private Integer userID;
    private String commentText;
    private LocalDateTime timestamp;

    public CommentsDto() {}

    public CommentsDto(Integer commentID, Integer postID, Integer userID,
                       String commentText, LocalDateTime timestamp) {
        this.commentID = commentID;
        this.postID = postID;
        this.userID = userID;
        this.commentText = commentText;
        this.timestamp = timestamp;
    }

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) {
        this.postID = postID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}