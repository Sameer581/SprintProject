package com.cg.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "comments")
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentID")
    private Long commentID;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postID")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID")
    private User user;

    @Column(name = "comment_text")
    private String commentText;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    
	public Comments() {}

	public Comments(Long commentID, Post post, User user, String commentText, LocalDateTime timestamp) {
		super();
		this.commentID = commentID;
		this.post = post;
		this.user = user;
		this.commentText = commentText;
		this.timestamp = timestamp;
	}

	public Long getCommentID() {
		return commentID;
	}

	public void setCommentID(Long commentID) {
		this.commentID = commentID;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
   