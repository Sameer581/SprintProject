package com.cg.dto;

import java.time.LocalDateTime;

public class CommentsDto {

    private Long commentID;
    private Long postID;
    private Long userID;
    private String commentText;
    private LocalDateTime timestamp;

    public CommentsDto() {}
    
	public CommentsDto(Long commentID, Long postID, Long userID, String commentText, LocalDateTime timestamp) {
		super();
		this.commentID = commentID;
		this.postID = postID;
		this.userID = userID;
		this.commentText = commentText;
		this.timestamp = timestamp;
	}



	public Long getCommentID() {
		return commentID;
	}

	public void setCommentID(Long commentID) {
		this.commentID = commentID;
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

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	}
    
