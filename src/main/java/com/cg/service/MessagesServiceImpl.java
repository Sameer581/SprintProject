package com.cg.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.MessagesDto;
import com.cg.entity.Messages;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.MessagesRepo;
import com.cg.repo.UserRepo;

@Service
public class MessagesServiceImpl implements MessagesService {

    @Autowired
    private MessagesRepo messagesRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Messages getMessage(Long messageId) {
        Optional<Messages> optMessage = messagesRepo.findById(messageId);

        if (optMessage.isPresent()) {
            return optMessage.get();
        }

        throw new NotAvailableException("Message not found " + messageId);
    }

    @Override
    public List<Messages> getAllMessages() {
        return messagesRepo.findAll();
    }

    @Override
    public Long sendMessage(MessagesDto dto) {
        User sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new NotAvailableException("Sender not found " + dto.getSenderId()));

        User receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new NotAvailableException("Receiver not found " + dto.getReceiverId()));

        Messages message = new Messages();
        message.setMessageText(dto.getMessageText());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        Messages savedMessage = messagesRepo.save(message);
        return savedMessage.getMessageId();
    }

    @Override
    public List<Messages> getMessagesBySenderId(Long senderId) {
        return messagesRepo.findBySenderUserId(senderId);
    }

    @Override
    public List<Messages> getMessagesByReceiverId(Long receiverId) {
        return messagesRepo.findByReceiverUserId(receiverId);
    }

    @Override
    public List<Messages> getConversation(Long user1, Long user2) {
        return messagesRepo.getConversation(user1, user2);
    }

    @Override
    public Long countMessagesBySender(Long senderId) {
        return messagesRepo.countBySenderUserId(senderId);
    }

    @Override
    public Messages updateMessage(Long id, MessagesDto dto) {
        Messages msg = messagesRepo.findById(id)
                .orElseThrow(() -> new NotAvailableException("Message not found " + id));

        msg.setMessageText(dto.getMessageText());
        return messagesRepo.save(msg);
    }

   
    
}