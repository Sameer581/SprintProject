package com.cg.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cg.dto.MessagesDto;
import com.cg.entity.Messages;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.MessagesRepo;
import com.cg.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class MessagesServiceTest {

    @Mock
    private MessagesRepo messagesRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private MessagesServiceImpl messagesService;

    private User sender;
    private User receiver;
    private Messages message;
    private MessagesDto dto;

    @BeforeEach
    void setUp() {
        sender = new User();
        sender.setUserID(1L);
        sender.setUsername("rahul");

        receiver = new User();
        receiver.setUserID(2L);
        receiver.setUsername("amit");

        message = new Messages();
        message.setMessageID(101L);
        message.setMessageText("Hello Amit");
        message.setSender(sender);
        message.setReceiver(receiver);

        dto = new MessagesDto();
        dto.setSenderID(1L);
        dto.setReceiverID(2L);
        dto.setMessageText("Hello Amit");
    }

    // SEND MESSAGE

    @Test
    void testSendMessage_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepo.findById(2L)).thenReturn(Optional.of(receiver));
        when(messagesRepo.save(any(Messages.class))).thenReturn(message);

        Long result = messagesService.sendMessage(dto);

        assertEquals(101L, result);
        verify(messagesRepo, times(1)).save(any(Messages.class));
    }

    @Test
    void testSendMessage_senderNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            messagesService.sendMessage(dto);
        });

        verify(messagesRepo, never()).save(any(Messages.class));
    }

    @Test
    void testSendMessage_receiverNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(sender));
        when(userRepo.findById(2L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            messagesService.sendMessage(dto);
        });

        verify(messagesRepo, never()).save(any(Messages.class));
    }

    // GET MESSAGE BY ID

    @Test
    void testGetMessage_success() {
        when(messagesRepo.findById(101L)).thenReturn(Optional.of(message));

        Messages result = messagesService.getMessage(101L);

        assertEquals(101L, result.getMessageID());
        assertEquals("Hello Amit", result.getMessageText());
    }

    @Test
    void testGetMessage_notFound() {
        when(messagesRepo.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            messagesService.getMessage(101L);
        });
    }

    // GET ALL MESSAGES

    @Test
    void testGetAllMessages_success() {
        Messages message2 = new Messages();
        message2.setMessageID(102L);
        message2.setMessageText("Hi Rahul");

        when(messagesRepo.findAll()).thenReturn(Arrays.asList(message, message2));

        List<Messages> result = messagesService.getAllMessages();

        assertEquals(2, result.size());
        assertEquals("Hello Amit", result.get(0).getMessageText());
        assertEquals("Hi Rahul", result.get(1).getMessageText());
    }

    @Test
    void testGetAllMessages_emptyList() {
        when(messagesRepo.findAll()).thenReturn(Arrays.asList());

        List<Messages> result = messagesService.getAllMessages();

        assertEquals(0, result.size());
    }

    // GET BY SENDER ID

    @Test
    void testGetMessagesBySenderID_success() {
        when(messagesRepo.findBySenderUserID(1L)).thenReturn(Arrays.asList(message));

        List<Messages> result = messagesService.getMessagesBySenderID(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getSender().getUserID());
    }

    // GET BY RECEIVER ID

    @Test
    void testGetMessagesByReceiverID_success() {
        when(messagesRepo.findByReceiverUserID(2L)).thenReturn(Arrays.asList(message));

        List<Messages> result = messagesService.getMessagesByReceiverID(2L);

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getReceiver().getUserID());
    }

    // GET CONVERSATION

    @Test
    void testGetConversation_success() {
        when(messagesRepo.getConversation(1L, 2L)).thenReturn(Arrays.asList(message));

        List<Messages> result = messagesService.getConversation(1L, 2L);

        assertEquals(1, result.size());
        assertEquals("Hello Amit", result.get(0).getMessageText());
    }

    // COUNT BY SENDER

    @Test
    void testCountMessagesBySender_success() {
        when(messagesRepo.countBySenderUserID(1L)).thenReturn(5L);

        Long result = messagesService.countMessagesBySender(1L);

        assertEquals(5L, result);
    }

    // UPDATE MESSAGE

    @Test
    void testUpdateMessage_success() {
        MessagesDto updateDto = new MessagesDto();
        updateDto.setMessageText("Updated Message");

        when(messagesRepo.findById(101L)).thenReturn(Optional.of(message));
        when(messagesRepo.save(any(Messages.class))).thenReturn(message);

        Messages result = messagesService.updateMessage(101L, updateDto);

        assertEquals("Updated Message", result.getMessageText());
        verify(messagesRepo, times(1)).save(message);
    }

    @Test
    void testUpdateMessage_notFound() {
        MessagesDto updateDto = new MessagesDto();
        updateDto.setMessageText("Updated Message");

        when(messagesRepo.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            messagesService.updateMessage(101L, updateDto);
        });

        verify(messagesRepo, never()).save(any(Messages.class));
    }
}