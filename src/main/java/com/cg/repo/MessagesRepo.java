package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.entity.Messages;

public interface MessagesRepo extends JpaRepository<Messages, Long> {
	List<Messages> findBySenderUserId(Long senderId);

    List<Messages> findByReceiverUserId(Long receiverId);
    
    @Query("SELECT m FROM Messages m WHERE " +
    	       "(m.sender.userId = :user1 AND m.receiver.userId = :user2) OR " +
    	       "(m.sender.userId = :user2 AND m.receiver.userId = :user1) " +
    	       "ORDER BY m.timestamp")
    	List<Messages> getConversation(Long user1, Long user2);

    	Long countBySenderUserId(Long senderId);
    }