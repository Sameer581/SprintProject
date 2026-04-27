package com.cg.dto;

import java.time.LocalDateTime;

public class CommentsDto {

<<<<<<< HEAD
    private Integer commentID;
    private Integer postID;
    private Integer userID;
=======
    private Long commentID;
    private Long postID;
    private Long userID;
>>>>>>> 26f938fe5dfa1c2b1ead5e61104464a56daeb59f
    private String commentText;
    private LocalDateTime timestamp;

    public CommentsDto() {}
<<<<<<< HEAD

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
=======
    
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
    
>>>>>>> 26f938fe5dfa1c2b1ead5e61104464a56daeb59f
