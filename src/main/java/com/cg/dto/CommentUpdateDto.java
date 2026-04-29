package com.cg.dto;

import jakarta.validation.constraints.NotBlank;

public class CommentUpdateDto {

    @NotBlank
    private String commentText;

	public String getCommentText() {
		return commentText;
	}

	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
    
}