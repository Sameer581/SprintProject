package com.cg.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "likes")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long LikeId;

	@ManyToOne
	@JoinColumn(name = "postid")
	private Post post;

	@ManyToOne
	@JoinColumn(name = "userid")
	private User user;

	public Like() {
		
	}
	public Like(Long likeId, Post post, User user) {
		super();
		LikeId = likeId;
		this.post = post;
		this.user = user;
	}
	public Long getLikeId() {
		return LikeId;
	}
	public void setLikeId(Long likeId) {
		LikeId = likeId;
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
}

	