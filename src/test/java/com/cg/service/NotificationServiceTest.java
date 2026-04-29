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

import com.cg.dto.NotificationDto;
import com.cg.entity.Notification;
import com.cg.entity.User;
import com.cg.exception.NotAvailableException;
import com.cg.repo.NotificationRepo;
import com.cg.repo.UserRepo;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @Mock
    private NotificationRepo notificationRepo;

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    private User user;
    private Notification notification;
    private NotificationDto notificationDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUserId(1L);
        user.setUsername("rahul");

        notification = new Notification();
        notification.setNotificationId(101L);
        notification.setContent("New message received");
        notification.setUser(user);

        notificationDto = new NotificationDto();
        notificationDto.setUserId(1L);
        notificationDto.setContent("New message received");
    }

    // ─── ADD NOTIFICATION ─────────────────────────────────────────────

    @Test
    void testAddNotification_success() {
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(notificationRepo.save(any(Notification.class))).thenReturn(notification);

        Long result = notificationService.addNotification(notificationDto);

        assertEquals(101L, result);
        verify(notificationRepo, times(1)).save(any(Notification.class));
    }

    @Test
    void testAddNotification_userNotFound() {
        when(userRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            notificationService.addNotification(notificationDto);
        });

        verify(notificationRepo, never()).save(any(Notification.class));
    }

    // ─── GET NOTIFICATION BY ID ───────────────────────────────────────

    @Test
    void testGetNotification_success() {
        when(notificationRepo.findById(101L)).thenReturn(Optional.of(notification));

        Notification result = notificationService.getNotification(101L);

        assertEquals(101L, result.getNotificationId());
        assertEquals("New message received", result.getContent());
    }

    @Test
    void testGetNotification_notFound() {
        when(notificationRepo.findById(101L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            notificationService.getNotification(101L);
        });
    }

    // ─── GET ALL NOTIFICATIONS ────────────────────────────────────────

    @Test
    void testGetAllNotifications_success() {
        Notification notification2 = new Notification();
        notification2.setNotificationId(102L);
        notification2.setContent("Friend request received");
        notification2.setUser(user);

        when(notificationRepo.findAll())
                .thenReturn(Arrays.asList(notification, notification2));

        List<Notification> result = notificationService.getAllNotifications();

        assertEquals(2, result.size());
        assertEquals("New message received", result.get(0).getContent());
        assertEquals("Friend request received", result.get(1).getContent());
    }

    @Test
    void testGetAllNotifications_emptyList() {
        when(notificationRepo.findAll()).thenReturn(Arrays.asList());

        List<Notification> result = notificationService.getAllNotifications();

        assertEquals(0, result.size());
    }

    // ─── GET NOTIFICATIONS BY USER ID ─────────────────────────────────

    @Test
    void testGetNotificationsByUserId_success() {
        when(notificationRepo.findByUserUserId(1L))
                .thenReturn(Arrays.asList(notification));

        List<Notification> result =
                notificationService.getNotificationsByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId().getUserId());
        assertEquals("New message received", result.get(0).getContent());
    }

    // ─── COUNT NOTIFICATIONS BY USER ID ───────────────────────────────

    @Test
    void testCountNotificationsByUserId_success() {
        when(notificationRepo.countByUserUserId(1L)).thenReturn(5L);

        Long result = notificationService.countNotificationsByUserId(1L);

        assertEquals(5L, result);
    }

    // ─── UPDATE NOTIFICATION ──────────────────────────────────────────

    @Test
    void testUpdateNotification_success() {
        NotificationDto updateDto = new NotificationDto();
        updateDto.setContent("Updated notification");

        when(notificationRepo.findById(101L))
                .thenReturn(Optional.of(notification));
        when(notificationRepo.save(any(Notification.class)))
                .thenReturn(notification);

        Notification result =
                notificationService.updateNotification(101L, updateDto);

        assertEquals("Updated notification", result.getContent());
        verify(notificationRepo, times(1)).save(notification);
    }

    @Test
    void testUpdateNotification_notFound() {
        NotificationDto updateDto = new NotificationDto();
        updateDto.setContent("Updated notification");

        when(notificationRepo.findById(101L))
                .thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            notificationService.updateNotification(101L, updateDto);
        });

        verify(notificationRepo, never()).save(any(Notification.class));
    }

    // ─── DELETE NOTIFICATION ──────────────────────────────────────────

    @Test
    void testDeleteNotification_success() {
        when(notificationRepo.findById(101L))
                .thenReturn(Optional.of(notification));

        notificationService.deleteNotification(101L);

        verify(notificationRepo, times(1)).delete(notification);
    }

    @Test
    void testDeleteNotification_notFound() {
        when(notificationRepo.findById(101L))
                .thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            notificationService.deleteNotification(101L);
        });

        verify(notificationRepo, never()).delete(any(Notification.class));
    }
}