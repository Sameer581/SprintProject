package com.cg.dto;
import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long commentID;
    private String commentText;
    private LocalDateTime timestamp;

    private Long postID;
    private String postContent;

    private Long userID;
    private String username;
    
    
	public CommentResponseDto() {
	}
	
	public CommentResponseDto(Long commentID, String commentText, LocalDateTime timestamp, Long postID,
			String postContent, Long userID, String username) {
		super();
		this.commentID = commentID;
		this.commentText = commentText;
		this.timestamp = timestamp;
		this.postID = postID;
		this.postContent = postContent;
		this.userID = userID;
		this.username = username;
	}
	
	public Long getCommentID() {
		return commentID;
	}
	public void setCommentID(Long commentID) {
		this.commentID = commentID;
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
	public Long getPostID() {
		return postID;
	}
	public void setPostID(Long postID) {
		this.postID = postID;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Long getUserID() {
		return userID;
	}
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

    
}