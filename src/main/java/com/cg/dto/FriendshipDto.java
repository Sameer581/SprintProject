package com.cg.dto;

import com.cg.enums.FriendshipStatus;

public class FriendshipDto {
	
	private Integer friendshipId; // Null for Requests, populated for Responses
    private Integer userId1;      // Sender
    private String username1;     // Populated for Responses
    private Integer userId2;      // Receiver
    private String username2;     // Populated for Responses
    private FriendshipStatus status; // Populated for Responses and Updates
    
    
    // No-args constructor (Required for JSON mapping)
    public FriendshipDto() {}

    
    // Constructor for Sending a Request (FriendRequestDto equivalent)
    public FriendshipDto(Integer userId1, Integer userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    
    // Constructor for Updates (UpdateFriendshipDto equivalent)
    public FriendshipDto(FriendshipStatus status) {
        this.status = status;
    }
    
    

    // All-args Constructor for Responses (FriendshipResponseDto equivalent)
    public FriendshipDto(Integer friendshipId, Integer userId1, String username1, 
                         Integer userId2, String username2, FriendshipStatus status) {
        this.friendshipId = friendshipId;
        this.userId1 = userId1;
        this.username1 = username1;
        this.userId2 = userId2;
        this.username2 = username2;
        this.status = status;
    }


    // Getters and Setters
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
