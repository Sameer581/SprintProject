package com.cg.service;

import java.util.List;

import com.cg.dto.NotificationDto;
import com.cg.entity.Notification;

public interface NotificationService {

    Long addNotification(NotificationDto dto);

    Notification getNotification(Long notificationId);

    List<Notification> getAllNotifications();

    List<Notification> getNotificationsByUserId(Long userId);

    Long countNotificationsByUserId(Long userId);

    Notification updateNotification(Long id, NotificationDto dto);

    void deleteNotification(Long id);
}