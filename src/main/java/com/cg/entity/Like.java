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
	private int LikeId;
	
	@ManyToOne
	@JoinColumn(name = "postid")
	private Post post;
	
	@ManyToOne
	@JoinColumn(name ="userid")
	private User User;

	public Like(int likeId, Post post, com.cg.entity.User user) {
		super();
		LikeId = likeId;
		this.post = post;
		User = user;
	}

	public int getLikeId() {
		return LikeId;
	}

	public void setLikeId(int likeId) {
		LikeId = likeId;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
        this.User = user;
    }
}

	