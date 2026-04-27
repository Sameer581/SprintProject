package com.cg.entity;

import com.cg.enums.FriendshipStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friends")
public class Friendship {

    @Id
    private Integer friendshipId;

    @ManyToOne
    @JoinColumn(name = "user_id1")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user_id2")
    private User user2;

    
    // Store an enum value in DB
    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
    
    
    
}