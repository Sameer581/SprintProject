package com.cg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.FriendshipDto;
import com.cg.entity.Friendship;
import com.cg.entity.User;
import com.cg.enums.FriendshipStatus;
import com.cg.exception.FriendshipAlreadyExistsException;
import com.cg.exception.InvalidFriendRequestException;
import com.cg.exception.NotAvailableException;
import com.cg.exception.UserNotFoundException;
import com.cg.repo.FriendshipRepo;
import com.cg.repo.UserRepo;


@Service
public class FriendshipServiceImpl implements FriendshipService {

    @Autowired
    private FriendshipRepo friendshipRepo;

    @Autowired
    private UserRepo userRepo;
    
    
    
    
    // Map to DTO
    private FriendshipDto mapToDto(Friendship friendship) {

        FriendshipDto dto = new FriendshipDto();

        dto.setFriendshipId(friendship.getFriendshipId());

        dto.setUserId1(friendship.getUser1().getUserId());
        dto.setUsername1(friendship.getUser1().getUsername()); 

        dto.setUserId2(friendship.getUser2().getUserId());
        dto.setUsername2(friendship.getUser2().getUsername()); 

        dto.setStatus(friendship.getStatus());

        return dto;
    }
    
   

    
    // Sending friend request
	@Override
	public FriendshipDto sendFriendRequest(FriendshipDto dto) {
		 User sender = userRepo.findById(dto.getUserId1())
	                .orElseThrow(() -> new UserNotFoundException("Sender not found"));

	        User receiver = userRepo.findById(dto.getUserId2())
	                .orElseThrow(() -> new UserNotFoundException("Receiver not found"));

	        
	        if (sender.getUserId().equals(receiver.getUserId())) {
	            throw new InvalidFriendRequestException(
	                "Cannot send a friend request to yourself"
	            );
	        }

	        
	        
	       // using JPQL method 
	        friendshipRepo.findByUsers(sender, receiver)
	                .ifPresent(f -> {
	                    throw new FriendshipAlreadyExistsException("Friendship already exists");
	                });

	        Friendship friendship = new Friendship();
	        friendship.setUser1(sender);
	        friendship.setUser2(receiver);
	        friendship.setStatus(FriendshipStatus.PENDING);

	        return mapToDto(friendshipRepo.save(friendship));
	}

	
	
	// accepting friend request
	@Override
	public FriendshipDto acceptFriendRequest(Long friendshipId) {
		Friendship friendship = friendshipRepo.findById(friendshipId)
                .orElseThrow(() -> new NotAvailableException("Friend request not found"));

        friendship.setStatus(FriendshipStatus.ACCEPTED);

        return mapToDto(friendshipRepo.save(friendship));
	}


	
	@Override
	public void rejectFriendRequest(Long friendshipId) {
	    Friendship friendship = friendshipRepo.findById(friendshipId)
	            .orElseThrow(() -> new NotAvailableException("Friend request not found"));

	    friendshipRepo.delete(friendship);
	}
	
	
	// get all the pending requests
	@Override
	public List<FriendshipDto> getPendingRequests(Long userId) {
		User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return friendshipRepo.findPendingRequestsForUser(user, FriendshipStatus.PENDING)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
	}
	

	
	// get friendship by id
	@Override
	public FriendshipDto getFriendshipById(Long friendshipId) {

        Friendship friendship = friendshipRepo.findById(friendshipId)
                .orElseThrow(() -> new NotAvailableException("Friendship not found"));

        return mapToDto(friendship);
	}

	
	
	// get friends by user id
	@Override
	public List<FriendshipDto> getFriendsByUserId(Long userId) {
		 User user = userRepo.findById(userId)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));

	        return friendshipRepo.findByUserAndStatus(user, FriendshipStatus.ACCEPTED)
	                .stream()
	                .map(this::mapToDto)
	                .collect(Collectors.toList());
	}


	
	// get all friendships
	@Override
	public List<FriendshipDto> getAllFriendships() {
		  return friendshipRepo.findAll()
	                .stream()
	                .map(this::mapToDto)
	                .collect(Collectors.toList());
	}

	
	// check friendship status between two users
//	@Override
//	public FriendshipDto checkFriendship(Long userId1, Long userId2) {
//
//        User u1 = userRepo.findById(userId1)
//                .orElseThrow(() -> new UserNotFoundException("User1 not found"));
//
//        User u2 = userRepo.findById(userId2)
//                .orElseThrow(() -> new UserNotFoundException("User2 not found"));
//
//        return friendshipRepo.findByUsers(u1, u2)
//                .map(this::mapToDto)
//                .orElse(null);
//	}
	
	
	
	// check friendship status between two users
	@Override
	public FriendshipDto checkFriendship(Long userId1, Long userId2) {

	    User u1 = userRepo.findById(userId1)
	            .orElseThrow(() -> new UserNotFoundException("User1 not found"));

	    User u2 = userRepo.findById(userId2)
	            .orElseThrow(() -> new UserNotFoundException("User2 not found"));

	    Friendship friendship = friendshipRepo.findByUsers(u1, u2)
	            .orElseThrow(() ->
	                    new NotAvailableException("No friendship exists between these users"));

	    return mapToDto(friendship);
	}

	
	
	// get friends count
	@Override
	public Long getFriendsCount(Long userId) {
	    return friendshipRepo.countByUserIdAndStatus(
	            userId,
	            FriendshipStatus.ACCEPTED
	    );
	}
	
	

    
}