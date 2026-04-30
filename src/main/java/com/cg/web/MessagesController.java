package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.MessagesDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.entity.Messages;
import com.cg.exception.ValidationException;
import com.cg.service.MessagesService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @GetMapping("/{id}")
    public Messages getMessage(@PathVariable Long id) {
        return messagesService.getMessage(id);
    }

    @GetMapping("/viewall")
    public List<Messages> getAllMessages() {
        return messagesService.getAllMessages();
    }

    @PostMapping("/send")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto sendMessage(@Valid @RequestBody MessagesDto dto, BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        Long mid = messagesService.sendMessage(dto);

        return new SuccessMessageDto("Message sent successfully with id ", mid);
    }

    @GetMapping("/sender/{senderID}")
    public List<Messages> getMessagesBySenderID(@PathVariable Long senderId) {
        return messagesService.getMessagesBySenderId(senderId);
    }

    @GetMapping("/receiver/{receiverID}")
    public List<Messages> getMessagesByReceiverID1(@PathVariable Long receiverId) {
        return messagesService.getMessagesByReceiverId(receiverId);
    }
    

    @GetMapping("/conversation/{user1}/{user2}")
    public List<Messages> getConversation(@PathVariable Long user1, @PathVariable Long user2) {
        return messagesService.getConversation(user1, user2);
    }

    @GetMapping("/sender/{senderID}/count")
    public Long countMessagesBySender(@PathVariable Long senderId) {
        return messagesService.countMessagesBySender(senderId);
    }
    

    @PutMapping("/{id}")
    public Messages updateMessage(@PathVariable Long id, @RequestBody MessagesDto dto) {
        return messagesService.updateMessage(id, dto);
    }
}
