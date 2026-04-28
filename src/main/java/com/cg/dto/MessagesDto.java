package com.cg.dto;

import java.time.LocalDateTime;

public class MessagesDTO {

    private Long messageID;
    private Long senderID;
    private Long receiverID;
    private String messageText;
    private LocalDateTime timestamp;

    public MessagesDTO() {
    }

    public MessagesDTO(Long messageID, Long senderID, Long receiverID, String messageText, LocalDateTime timestamp) {
        this.messageID = messageID;
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public Long getSenderID() {
        return senderID;
    }

    public void setSenderID(Long senderID) {
        this.senderID = senderID;
    }

    public Long getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(Long receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}