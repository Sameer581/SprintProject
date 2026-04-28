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
    public Messages getMessage(Long messageID) {
        Optional<Messages> optMessage = messagesRepo.findById(messageID);

        if (optMessage.isPresent()) {
            return optMessage.get();
        }

        throw new NotAvailableException("Message not found " + messageID);
    }

    @Override
    public List<Messages> getAllMessages() {
        return messagesRepo.findAll();
    }

    @Override
    public Long sendMessage(MessagesDto dto) {
        User sender = userRepo.findById(dto.getSenderID())
                .orElseThrow(() -> new NotAvailableException("Sender not found " + dto.getSenderID()));

        User receiver = userRepo.findById(dto.getReceiverID())
                .orElseThrow(() -> new NotAvailableException("Receiver not found " + dto.getReceiverID()));

        Messages message = new Messages();
        message.setMessageText(dto.getMessageText());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        Messages savedMessage = messagesRepo.save(message);
        return savedMessage.getMessageID();
    }

    @Override
    public List<Messages> getMessagesBySenderID(Long senderID) {
        return messagesRepo.findBySenderUserID(senderID);
    }

    @Override
    public List<Messages> getMessagesByReceiverID(Long receiverID) {
        return messagesRepo.findByReceiverUserID(receiverID);
    }

    @Override
    public List<Messages> getConversation(Long user1, Long user2) {
        return messagesRepo.getConversation(user1, user2);
    }

    @Override
    public Long countMessagesBySender(Long senderID) {
        return messagesRepo.countBySenderUserID(senderID);
    }

    @Override
    public Messages updateMessage(Long id, MessagesDto dto) {
        Messages msg = messagesRepo.findById(id)
                .orElseThrow(() -> new NotAvailableException("Message not found " + id));

        msg.setMessageText(dto.getMessageText());
        return messagesRepo.save(msg);
    }

   
    
}