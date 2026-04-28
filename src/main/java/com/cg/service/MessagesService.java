package com.cg.service;

import java.util.List;

import com.cg.dto.MessagesDto;
import com.cg.entity.Messages;

public interface MessagesService {

    public Long sendMessage(MessagesDto dto);

    public Messages getMessage(Long messageID);

    public List<Messages> getAllMessages();

    public List<Messages> getMessagesBySenderID(Long senderID);

    public List<Messages> getMessagesByReceiverID(Long receiverID);
    List<Messages> getConversation(Long user1, Long user2);

    Long countMessagesBySender(Long senderID);
    
    Messages updateMessage(Long id, MessagesDto dto);
}