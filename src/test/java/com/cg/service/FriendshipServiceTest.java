package com.cg.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.FriendshipDto;
import com.cg.entity.Friendship;
import com.cg.entity.User;
import com.cg.enums.FriendshipStatus;
import com.cg.exception.FriendshipAlreadyExistsException;
import com.cg.exception.InvalidFriendRequestException;
import com.cg.repo.FriendshipRepo;
import com.cg.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class FriendshipServiceTest {

	
	@Mock
    private FriendshipRepo friendshipRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;
    
    
    @Test
    void sendFriendRequest_success() {
        // Arrange
        FriendshipDto input = new FriendshipDto();
        input.setUserId1(1L);
        input.setUserId2(2L);

        User sender = new User();
        sender.setUserId(1L);
        sender.setUsername("Alice");

        User receiver = new User();
        receiver.setUserId(2L);
        receiver.setUsername("Bob");

        Friendship savedFriendship = new Friendship();
        savedFriendship.setFriendshipId(100L);
        savedFriendship.setUser1(sender);
        savedFriendship.setUser2(receiver);
        savedFriendship.setStatus(FriendshipStatus.PENDING);

        when(userRepo.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepo.findById(2L)).thenReturn(Optional.of(receiver));
        when(friendshipRepo.findByUsers(sender, receiver))
                .thenReturn(Optional.empty());
        when(friendshipRepo.save(any(Friendship.class)))
                .thenReturn(savedFriendship);

        // Act
        FriendshipDto result = friendshipService.sendFriendRequest(input);

        // Assert
        assertNotNull(result);
        assertEquals(100L, result.getFriendshipId());
        assertEquals("Alice", result.getUsername1());
        assertEquals("Bob", result.getUsername2());
        assertEquals(FriendshipStatus.PENDING, result.getStatus());

        verify(friendshipRepo).save(any(Friendship.class));
    }
    
    
    @Test
    void sendFriendRequest_sameUser_throwsException() {
        FriendshipDto input = new FriendshipDto();
        input.setUserId1(1L);
        input.setUserId2(1L);

        User user = new User();
        user.setUserId(1L);

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        assertThrows(
            InvalidFriendRequestException.class,
            () -> friendshipService.sendFriendRequest(input)
        );

        verify(friendshipRepo, never()).save(any());
    }
    
    
    @Test
    void sendFriendRequest_alreadyExists_throwsException() {
        FriendshipDto input = new FriendshipDto();
        input.setUserId1(1L);
        input.setUserId2(2L);

        User sender = new User();
        sender.setUserId(1L);

        User receiver = new User();
        receiver.setUserId(2L);

        Friendship existing = new Friendship();

        when(userRepo.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepo.findById(2L)).thenReturn(Optional.of(receiver));
        when(friendshipRepo.findByUsers(sender, receiver))
                .thenReturn(Optional.of(existing));

        assertThrows(
            FriendshipAlreadyExistsException.class,
            () -> friendshipService.sendFriendRequest(input)
        );

        verify(friendshipRepo, never()).save(any());
    }
    
    
    @Test
    void acceptFriendRequest_success() {
        User user1 = new User();
        user1.setUserId(1L);
        user1.setUsername("Alice");

        User user2 = new User();
        user2.setUserId(2L);
        user2.setUsername("Bob");

        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1L);
        friendship.setUser1(user1);
        friendship.setUser2(user2);
        friendship.setStatus(FriendshipStatus.PENDING);

        when(friendshipRepo.findById(1L))
                .thenReturn(Optional.of(friendship));
        when(friendshipRepo.save(any(Friendship.class)))
                .thenReturn(friendship);

        FriendshipDto result =
                friendshipService.acceptFriendRequest(1L);

        assertEquals(FriendshipStatus.ACCEPTED, result.getStatus());

        verify(friendshipRepo).save(friendship);
    }
    
    
    @Test
    void rejectFriendRequest_success() {
        Friendship friendship = new Friendship();
        friendship.setFriendshipId(1L);

        when(friendshipRepo.findById(1L))
                .thenReturn(Optional.of(friendship));

        friendshipService.rejectFriendRequest(1L);

        verify(friendshipRepo).delete(friendship);
    }
    
    
    @Test
    void getFriendsCount_success() {
        when(friendshipRepo.countByUserIdAndStatus(
                1L, FriendshipStatus.ACCEPTED))
                .thenReturn(5L);

        Long count = friendshipService.getFriendsCount(1L);

        assertEquals(5L, count);
    }
    
    
    
    
    
}
