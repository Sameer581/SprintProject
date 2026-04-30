package com.cg.dto;

import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;



public class MessagesDto {

    private Long messageId;
    
    @NotNull
    private Long senderId;
    
    @NotNull
    private  Long receiverId;
   
    @NotBlank
    private String messageText;
    private LocalDateTime timestamp;
    
	public MessagesDto() {
	}
	public MessagesDto(Long messageId, @NotNull Long senderId, @NotNull Long receiverId, @NotBlank String messageText,
			LocalDateTime timestamp) {
		super();
		this.messageId = messageId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.messageText = messageText;
		this.timestamp = timestamp;
	}


	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public Long getSenderId() {
		return senderId;
	}
	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}
	public Long getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Long receiverId) {
		this.receiverId = receiverId;
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
   