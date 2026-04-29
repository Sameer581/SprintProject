package com.cg.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentsDto {
	@NotNull
    private Long postId;
	@NotNull
    private Long userId;
    @NotBlank
	private String commentText;
    
	public CommentsDto() {

	}
	public CommentsDto(@NotNull Long postId, @NotNull Long userId, @NotBlank String commentText) {
		super();
		this.postId = postId;
		this.userId = userId;
		this.commentText = commentText;
	}
	public Long getPostId() {
		return postId;
	}
	public void setPostId(Long postId) {
		this.postId = postId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
}
    
    