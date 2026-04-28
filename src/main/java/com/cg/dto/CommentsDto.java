package com.cg.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentsDto {
	@NotNull
    private Long postID;
	@NotNull
    private Long userID;
    @NotBlank
	private String commentText;

    public CommentsDto() {}
    
	public CommentsDto(Long postID, Long userID, String commentText) {
		super();
		this.postID = postID;
		this.userID = userID;
		this.commentText = commentText;
	}
	
	public Long getPostID() {
		return postID;
	}

	public void setPostID(Long postID) {
		this.postID = postID;
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	}
    
