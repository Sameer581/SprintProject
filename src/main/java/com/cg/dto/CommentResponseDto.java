package com.cg.dto;
import java.time.LocalDateTime;

public class CommentResponseDto {

    private Long commentId;
    private String commentText;
    private LocalDateTime timestamp;

    private Long postId;
    private String postContent;

    private Long userId;
    private String username;
    
	public CommentResponseDto() {
	}
	
	public CommentResponseDto(Long commentId, String commentText, LocalDateTime timestamp, Long postId,
			String postContent, Long userId, String username) {
		super();
		this.commentId = commentId;
		this.commentText = commentText;
		this.timestamp = timestamp;
		this.postId = postId;
		this.postContent = postContent;
		this.userId = userId;
		this.username = username;
	}
	public Long getCommentId() {
		return commentId;
	}
	public void setCommentId(Long commentId) {
		this.commentId = commentId;
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
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public String getPostContent() {
		return postContent;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
}
    