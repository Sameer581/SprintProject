package com.cg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.dto.NotificationDto;
import com.cg.entity.Notification;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.NotificationRepo;
import com.cg.repo.UserRepo;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepo notificationRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public Notification getNotification(Long notificationId) {

        Optional<Notification> optNotification =
                notificationRepo.findById(notificationId);

        if (optNotification.isPresent()) {
            return optNotification.get();
        }

        throw new NotAvailableException(
                "Notification not found " + notificationId);
    }

    @Override
    public List<Notification> getAllNotifications() {
        return notificationRepo.findAll();
    }

    @Override
    public Long addNotification(NotificationDto dto) {

        Optional<User> optUser =
                userRepo.findById(dto.getUserId());

        if (optUser.isEmpty()) {
            throw new NotAvailableException(
                    "User not found " + dto.getUserId());
        }

        Notification notification = new Notification();
        notification.setContent(dto.getContent());
        notification.setTimestamp(java.time.LocalDateTime.now());
        notification.setUser(optUser.get());

        Notification savedNotification =
                notificationRepo.save(notification);

        return savedNotification.getNotificationId();
    }

    @Override
    public List<Notification> getNotificationsByUserId(Long userID) {
        return notificationRepo.findByUserUserID(userID);
    }
    
    @Override
    public Long countNotificationsByUserId(Long userId) {
        return notificationRepo.countByUserUserID(userId);
    }

    @Override
    public Notification updateNotification(Long id, NotificationDto dto) {

        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new NotAvailableException("Notification not found " + id));

        notification.setContent(dto.getContent());

        return notificationRepo.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {

        Notification notification = notificationRepo.findById(id)
                .orElseThrow(() -> new NotAvailableException("Notification not found " + id));

        notificationRepo.delete(notification);
    }
}