package com.cg.entity;

import com.cg.enums.FriendshipStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friends")
public class Friendship {

    @Id
    private Integer friendshipId;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private User user2;

    
    // Store an enum value in DB
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;


	public Integer getFriendshipId() {
		return friendshipId;
	}


	public void setFriendshipId(Integer friendshipId) {
		this.friendshipId = friendshipId;
	}


	public User getUser1() {
		return user1;
	}


	public void setUser1(User user1) {
		this.user1 = user1;
	}


	public User getUser2() {
		return user2;
	}


	public void setUser2(User user2) {
		this.user2 = user2;
	}


	public FriendshipStatus getStatus() {
		return status;
	}


	public void setStatus(FriendshipStatus status) {
		this.status = status;
	}
    
    
    
    
}