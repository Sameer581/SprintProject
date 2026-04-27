package com.cg.dto;

import com.cg.enums.FriendshipStatus;

//RESPONSE DTO - to send back to the client
public class FriendshipResponseDTO {
	
	
	private Integer friendshipId;
    private Integer userId1;
    private String username1;
    private Integer userId2;
    private String username2;
    private FriendshipStatus status;


// No-args constructor
 public FriendshipResponseDTO() {}
 
 

 // All-args constructor
 public FriendshipResponseDTO(Integer friendshipId, Integer userId1, String username1, 
         Integer userId2, String username2, FriendshipStatus status) {
this.friendshipId = friendshipId;
this.userId1 = userId1;
this.username1 = username1;
this.userId2 = userId2;
this.username2 = username2;
this.status = status;
}
 
 public Integer getFriendshipId() {
	return friendshipId;
 }
 
 public void setFriendshipId(Integer friendshipId) {
	this.friendshipId = friendshipId;
 }
 
 public Integer getUserId1() {
	return userId1;
 }
 
 public void setUserId1(Integer userId1) {
	this.userId1 = userId1;
 }
 
 public String getUsername1() {
	return username1;
 }
 
 public void setUsername1(String username1) {
	this.username1 = username1;
 }
 public Integer getUserId2() {
	return userId2;
 }
 
 public void setUserId2(Integer userId2) {
	this.userId2 = userId2;
 }
 public String getUsername2() {
	return username2;
 }
 public void setUsername2(String username2) {
	this.username2 = username2;
 }
 
 public FriendshipStatus getStatus() {
	return status;
 }
 
 public void setStatus(FriendshipStatus status) {
	this.status = status;
 }
 
 
 
}