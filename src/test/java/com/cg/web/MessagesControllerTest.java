package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cg.dto.MessagesDto;
import com.cg.entity.Messages;
import com.cg.entity.User;
import com.cg.service.MessagesService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class MessagesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MessagesService messagesService;

    @InjectMocks
    private MessagesController messagesController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Messages message;
    private MessagesDto messagesDto;
    private User sender;
    private User receiver;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(messagesController).build();

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

        messagesDto = new MessagesDto();
        messagesDto.setSenderID(1L);
        messagesDto.setReceiverID(2L);
        messagesDto.setMessageText("Hello Amit");
    }

    // ─── GET /messages/{id} ─────────────────────────────────────────────

    @Test
    void testGetMessage_success() throws Exception {
        when(messagesService.getMessage(101L)).thenReturn(message);

        mockMvc.perform(get("/messages/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageID").value(101))
                .andExpect(jsonPath("$.messageText").value("Hello Amit"));
    }

    // ─── GET /messages/viewall ──────────────────────────────────────────

    @Test
    void testGetAllMessages_success() throws Exception {
        when(messagesService.getAllMessages()).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/messages/viewall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].messageText").value("Hello Amit"));
    }

    // ─── POST /messages/send ────────────────────────────────────────────

    @Test
    void testSendMessage_success() throws Exception {
        when(messagesService.sendMessage(any(MessagesDto.class))).thenReturn(101L);

        mockMvc.perform(post("/messages/send")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(messagesDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg").value("Message sent successfully with id "))
                .andExpect(jsonPath("$.id").value(101));
    }

    // ─── GET /messages/sender/{senderID} ────────────────────────────────

    @Test
    void testGetMessagesBySenderID_success() throws Exception {
        when(messagesService.getMessagesBySenderID(1L)).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/messages/sender/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].messageText").value("Hello Amit"));
    }

    // ─── GET /messages/receiver/{receiverID} ────────────────────────────

    @Test
    void testGetMessagesByReceiverID_success() throws Exception {
        when(messagesService.getMessagesByReceiverID(2L)).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/messages/receiver/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].messageText").value("Hello Amit"));
    }

    // ─── GET /messages/conversation/{user1}/{user2} ─────────────────────

    @Test
    void testGetConversation_success() throws Exception {
        when(messagesService.getConversation(1L, 2L)).thenReturn(Arrays.asList(message));

        mockMvc.perform(get("/messages/conversation/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].messageText").value("Hello Amit"));
    }

    // ─── GET /messages/sender/{senderID}/count ──────────────────────────

    @Test
    void testCountMessagesBySender_success() throws Exception {
        when(messagesService.countMessagesBySender(1L)).thenReturn(5L);

        mockMvc.perform(get("/messages/sender/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // ─── PUT /messages/{id} ─────────────────────────────────────────────

    @Test
    void testUpdateMessage_success() throws Exception {
        Messages updatedMessage = new Messages();
        updatedMessage.setMessageID(101L);
        updatedMessage.setMessageText("Updated Message");
        updatedMessage.setSender(sender);
        updatedMessage.setReceiver(receiver);

        MessagesDto updateDto = new MessagesDto();
        updateDto.setMessageText("Updated Message");

        when(messagesService.updateMessage(eq(101L), any(MessagesDto.class)))
                .thenReturn(updatedMessage);

        mockMvc.perform(put("/messages/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageID").value(101))
                .andExpect(jsonPath("$.messageText").value("Updated Message"));
    }
}