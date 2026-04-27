package com.cg.dto;

import com.cg.enums.FriendshipStatus;

//UPDATE DTO - for accepting or rejecting a friend request
public class UpdateFriendshipDto {
	
	
 private FriendshipStatus status;     // PENDING → ACCEPTED

 
 public FriendshipStatus getStatus() {
	return status;
 }

 public void setStatus(FriendshipStatus status) {
	this.status = status;
 }
 
 
}