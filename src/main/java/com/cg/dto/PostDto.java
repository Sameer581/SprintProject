package com.cg.dto;

import java.sql.Timestamp;
import java.util.List;

public class PostDto {

    private Long postID;
    private String content;
    private Timestamp timestamp;
    private UserDto user;
    private List<CommentsDto> comments;
    private List<LikeDto> likes;

    public PostDto() {}

    public PostDto(Long postID, String content, Timestamp timestamp,
                   UserDto user, List<CommentsDto> comments, List<LikeDto> likes) {
        this.postID = postID;
        this.content = content;
        this.timestamp = timestamp;
        this.user = user;
        this.comments = comments;
        this.likes = likes;
    }

    public Long getPostID() {
        return postID;
    }

    public void setPostID(Long postID) {
        this.postID = postID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public List<CommentsDto> getComments() {
        return comments;
    }

    public void setComments(List<CommentsDto> comments) {
        this.comments = comments;
    }

    public List<LikeDto> getLikes() {
        return likes;
    }

    public void setLikes(List<LikeDto> likes) {
        this.likes = likes;
    }
}