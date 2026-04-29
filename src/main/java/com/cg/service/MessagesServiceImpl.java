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
<<<<<<< HEAD
    public Messages getMessage(Long messageID) {
        Optional<Messages> optMessage = messagesRepo.findById(messageID);
=======
    public Messages getMessage(Long messageId) {
        Optional<Messages> optMessage = messagesRepo.findById(messageId);
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3

        if (optMessage.isPresent()) {
            return optMessage.get();
        }

<<<<<<< HEAD
        throw new NotAvailableException("Message not found " + messageID);
=======
        throw new NotAvailableException("Message not found " + messageId);
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3
    }

    @Override
    public List<Messages> getAllMessages() {
        return messagesRepo.findAll();
    }

    @Override
    public Long sendMessage(MessagesDto dto) {
<<<<<<< HEAD
        User sender = userRepo.findById(dto.getSenderID())
                .orElseThrow(() -> new NotAvailableException("Sender not found " + dto.getSenderID()));

        User receiver = userRepo.findById(dto.getReceiverID())
                .orElseThrow(() -> new NotAvailableException("Receiver not found " + dto.getReceiverID()));
=======
        User sender = userRepo.findById(dto.getSenderId())
                .orElseThrow(() -> new NotAvailableException("Sender not found " + dto.getSenderId()));

        User receiver = userRepo.findById(dto.getReceiverId())
                .orElseThrow(() -> new NotAvailableException("Receiver not found " + dto.getReceiverId()));
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3

        Messages message = new Messages();
        message.setMessageText(dto.getMessageText());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setReceiver(receiver);

        Messages savedMessage = messagesRepo.save(message);
<<<<<<< HEAD
        return savedMessage.getMessageID();
    }

    @Override
    public List<Messages> getMessagesBySenderID(Long senderID) {
        return messagesRepo.findBySenderUserID(senderID);
    }

    @Override
    public List<Messages> getMessagesByReceiverID(Long receiverID) {
        return messagesRepo.findByReceiverUserID(receiverID);
=======
        return savedMessage.getMessageId();
    }

    @Override
    public List<Messages> getMessagesBySenderId(Long senderId) {
        return messagesRepo.findBySenderUserId(senderId);
    }

    @Override
    public List<Messages> getMessagesByReceiverId(Long receiverId) {
        return messagesRepo.findByReceiverUserId(receiverId);
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3
    }

    @Override
    public List<Messages> getConversation(Long user1, Long user2) {
        return messagesRepo.getConversation(user1, user2);
    }

    @Override
<<<<<<< HEAD
    public Long countMessagesBySender(Long senderID) {
        return messagesRepo.countBySenderUserID(senderID);
=======
    public Long countMessagesBySender(Long senderId) {
        return messagesRepo.countBySenderUserId(senderId);
>>>>>>> 7f73ceadb51d4a7b9263867f5db36755cae608a3
    }

    @Override
    public Messages updateMessage(Long id, MessagesDto dto) {
        Messages msg = messagesRepo.findById(id)
                .orElseThrow(() -> new NotAvailableException("Message not found " + id));

        msg.setMessageText(dto.getMessageText());
        return messagesRepo.save(msg);
    }

   
    
}