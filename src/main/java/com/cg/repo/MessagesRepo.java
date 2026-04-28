package com.cg.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cg.entity.Messages;

public interface MessagesRepo extends JpaRepository<Messages, Long> {
	List<Messages> findBySenderUserID(Long senderID);

    List<Messages> findByReceiverUserID(Long receiverID);
    
    @Query("SELECT m FROM Messages m WHERE " +
    	       "(m.sender.userID = :user1 AND m.receiver.userID = :user2) OR " +
    	       "(m.sender.userID = :user2 AND m.receiver.userID = :user1) " +
    	       "ORDER BY m.timestamp")
    	List<Messages> getConversation(Long user1, Long user2);

    	Long countBySenderUserID(Long senderID);
    }