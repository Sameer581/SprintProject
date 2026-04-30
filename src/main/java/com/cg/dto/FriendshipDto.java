package com.cg.dto;

import com.cg.enums.FriendshipStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;


public class FriendshipDto {
	
	private Long friendshipId; // Null for Requests, populated for Responses only
	
	@NotNull(message = "Sender userId1 cannot be null")
	@Min(value = 1, message = "UserId must be greater than 0")
    private Long userId1;      // Sender
	
    private String username1;     // Populated for Responses
    
    
    @NotNull(message = "Receiver userId2 cannot be null")
    @Min(value = 1, message = "UserId must be greater than 0")
    private Long userId2;      // Receiver
    
    private String username2;     // Populated for Responses
    
    private FriendshipStatus status; // Populated for Responses and Updates
    
    
    
    // No-args constructor (Required for JSON mapping)
    public FriendshipDto() {}

    
    // Constructor for Sending a Request (FriendRequestDto equivalent)
    public FriendshipDto(Long userId1, Long userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }

    
    // Constructor for Updates (UpdateFriendshipDto equivalent)
    public FriendshipDto(FriendshipStatus status) {
        this.status = status;
    }
    
    

    // All-args Constructor for Responses (FriendshipResponseDto equivalent)
    public FriendshipDto(Long friendshipId, Long userId1, String username1, 
                         Long userId2, String username2, FriendshipStatus status) {
        this.friendshipId = friendshipId;
        this.userId1 = userId1;
        this.username1 = username1;
        this.userId2 = userId2;
        this.username2 = username2;
        this.status = status;
    }


    
    // Getters and Setters
    
	public Long getFriendshipId() {
		return friendshipId;
	}


	public void setFriendshipId(Long friendshipId) {
		this.friendshipId = friendshipId;
	}


	public Long getUserId1() {
		return userId1;
	}


	public void setUserId1(Long userId1) {
		this.userId1 = userId1;
	}


	public String getUsername1() {
		return username1;
	}


	public void setUsername1(String username1) {
		this.username1 = username1;
	}


	public Long getUserId2() {
		return userId2;
	}


	public void setUserId2(Long userId2) {
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
