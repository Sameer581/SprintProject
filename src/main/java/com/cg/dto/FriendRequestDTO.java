package com.cg.dto;

import com.cg.enums.FriendshipStatus;

public class FriendRequestDTO {
	

	private Integer userId1; // Sender
    private Integer userId2; // Receiver

    
    // No-args constructor
    public FriendRequestDTO() {}

    
    // All args constructor
    public FriendRequestDTO(Integer userId1, Integer userId2) {
        this.userId1 = userId1;
        this.userId2 = userId2;
    }


	public Integer getUserId1() {
		return userId1;
	}


	public void setUserId1(Integer userId1) {
		this.userId1 = userId1;
	}


	public Integer getUserId2() {
		return userId2;
	}


	public void setUserId2(Integer userId2) {
		this.userId2 = userId2;
	}
    
   
}
