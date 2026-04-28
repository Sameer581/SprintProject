package com.cg.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificationDto {

    private Long notificationId;

    @NotNull
    private Long userId;

    @NotBlank
    private String content;

    private LocalDateTime timestamp;

    public NotificationDTO() {
    }

    public NotificationDto(Long notificationId, Long userId, String content, LocalDateTime timestamp) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}