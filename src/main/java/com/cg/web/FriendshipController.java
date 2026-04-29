package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.dto.FriendshipDto;
import com.cg.service.FriendshipService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/friendships")
public class FriendshipController {
	

    @Autowired
    private FriendshipService friendshipService;
    

    // 1. Send friend request
    @PostMapping("/request")
    public ResponseEntity<FriendshipDto> sendFriendRequest(@Valid @RequestBody FriendshipDto dto) {
        FriendshipDto createdRequest = friendshipService.sendFriendRequest(dto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }
    

    // 2. Accept friend request
    @PutMapping("/{friendshipId}/accept")
    public ResponseEntity<FriendshipDto> acceptFriendRequest(@PathVariable Long friendshipId) {
        FriendshipDto acceptedRequest = friendshipService.acceptFriendRequest(friendshipId);
        return ResponseEntity.ok(acceptedRequest);
    }
    

    
    // 3. Reject friend request
//    @DeleteMapping("/{friendshipId}/reject")
//    public ResponseEntity<String> rejectFriendRequest(@PathVariable Long friendshipId) {
//        friendshipService.rejectFriendRequest(friendshipId);
//        return ResponseEntity.ok("Friend request rejected successfully.");
//    }

    
    // 4. Get pending friend requests for a user
    @GetMapping("/users/{userId}/pending")
    public ResponseEntity<List<FriendshipDto>> getPendingRequests(@PathVariable Long userId) {
        List<FriendshipDto> pendingRequests = friendshipService.getPendingRequests(userId);
        return ResponseEntity.ok(pendingRequests);
    }

    
    // 5. Get friendship by ID
    @GetMapping("/{friendshipId}")
    public ResponseEntity<FriendshipDto> getFriendshipById(@PathVariable Long friendshipId) {
        FriendshipDto friendship = friendshipService.getFriendshipById(friendshipId);
        return ResponseEntity.ok(friendship);
    }
    

    // 6. Get all accepted friends of a user
    @GetMapping("/users/{userId}/friends")
    public ResponseEntity<List<FriendshipDto>> getFriendsByUserId(@PathVariable Long userId) {
        List<FriendshipDto> friends = friendshipService.getFriendsByUserId(userId);
        return ResponseEntity.ok(friends);
    }

    
    // 7. Get all friendships
    @GetMapping
    public ResponseEntity<List<FriendshipDto>> getAllFriendships() {
        List<FriendshipDto> friendships = friendshipService.getAllFriendships();
        return ResponseEntity.ok(friendships);
    }

    
    // 8. Check friendship status between two users
    @GetMapping("/check")
    public ResponseEntity<FriendshipDto> checkFriendship(
            @RequestParam Long userId1,
            @RequestParam Long userId2) {
        FriendshipDto friendship = friendshipService.checkFriendship(userId1, userId2);
        return ResponseEntity.ok(friendship);
    }

    
    // 9. Get total friends count for a user
    @GetMapping("/users/{userId}/friends/count")
    public ResponseEntity<Long> getFriendsCount(@PathVariable Long userId) {
        Long count = friendshipService.getFriendsCount(userId);
        return ResponseEntity.ok(count);
    }
    
    
}