package com.cg.dto;

import java.time.LocalDateTime;

public class NotificationDto {

    private Integer notificationId;
    private Integer userId;
    private String content;
    private LocalDateTime timestamp;

    public NotificationDto() {
    }

    public NotificationDto(Integer notificationId, Integer userId, String content, LocalDateTime timestamp) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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