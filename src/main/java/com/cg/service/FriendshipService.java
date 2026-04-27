package com.cg.service;

import java.util.List;

import com.cg.dto.FriendshipDto;
import com.cg.enums.FriendshipStatus;

public interface FriendshipService {
	
	 FriendshipDto sendFriendRequest(FriendshipDto dto);                  // Endpoint 1
	
	 FriendshipDto updateFriendshipStatus(Integer friendshipId,           // Endpoint 2
	                                   FriendshipStatus status);      
	   
	    void deleteFriendship(Integer friendshipId);                        
	    // Endpoint 3,4
	    List<FriendshipDto> getFriendsByUserId(Integer userId);            // Endpoint 5
	    
	    List<FriendshipDto> getPendingRequestsForUser(Integer userId);     // Endpoint 6
	    
	    FriendshipDto getFriendshipById(Integer friendshipId);             // Endpoint 7
	    
	    List<FriendshipDto> getAllFriendships();                            // Endpoint 8
	    
	    FriendshipDto checkFriendship(Integer userId1, Integer userId2);   // Endpoint 9
	    
	    Integer getFriendsCount(Integer userId);                           // Endpoint 10

}
