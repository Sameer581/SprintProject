package com.cg.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class Messages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageID")
    private Long messageID;

    @ManyToOne
    @JoinColumn(name = "senderID", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverID", nullable = false)
    private User receiver;

    @Column(name = "message_text", nullable = false)
    private String messageText;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Messages() {
    }

    public Messages(Long messageID, User sender, User receiver, String messageText, LocalDateTime timestamp) {
        super();
        this.messageID = messageID;
        this.sender = sender;
        this.receiver = receiver;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public Long getMessageID() {
        return messageID;
    }

    public void setMessageID(Long messageID) {
        this.messageID = messageID;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
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