package com.cg.service;

import java.util.List;

import com.cg.dto.FriendshipDto;
import com.cg.enums.FriendshipStatus;

public interface FriendshipService {
	
	// total 10 end points
	
	// Sending friend request
	 FriendshipDto sendFriendRequest(FriendshipDto dto);                  
	 
	 // accepting friend request
	 FriendshipDto acceptFriendRequest(Long friendshipId);                
	 
	 // rejecting friend request
//	 void rejectFriendRequest(Long friendshipId);
	 
	  void rejectFriendRequest(Long friendshipId);
	 
	 // get all pending requests from users
	 List<FriendshipDto> getPendingRequests(Long userId);
	 
	 // get friendship by friendshipId
	 FriendshipDto getFriendshipById(Long friendshipId);
	 
	 // friends by user id
	 List<FriendshipDto> getFriendsByUserId(Long userId);  
	              
	 // finding list of all friendships
	 List<FriendshipDto> getAllFriendships();                            
	    
	 FriendshipDto checkFriendship(Long userId1, Long userId2);   
	    
	 // get number of friends count
	 Long getFriendsCount(Long userId);                           

	 // deleting/removing friend
	 // void removeFriend(Integer friendshipId);

}
