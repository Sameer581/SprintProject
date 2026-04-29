package com.cg.service;

import java.util.List;

import com.cg.dto.MessagesDto;
import com.cg.entity.Messages;

public interface MessagesService {

    public Long sendMessage(MessagesDto dto);

    public Messages getMessage(Long messageId);

    public List<Messages> getAllMessages();

    public List<Messages> getMessagesBySenderId(Long senderId);

    public List<Messages> getMessagesByReceiverId(Long receiverId);
    List<Messages> getConversation(Long user1, Long user2);

    Long countMessagesBySender(Long senderId);
    
    Messages updateMessage(Long id, MessagesDto dto);
}