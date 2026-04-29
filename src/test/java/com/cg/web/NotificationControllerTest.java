package com.cg.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

import com.cg.dto.NotificationDto;
import com.cg.entity.Notification;
import com.cg.entity.User;
import com.cg.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private NotificationController notificationController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Notification notification;
    private NotificationDto notificationDto;
    private User user;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(notificationController).build();

        user = new User();
        user.setUserId(1L);
        user.setUsername("rahul");

        notification = new Notification();
        notification.setNotificationId(101L);
        notification.setContent("New notification");
        notification.setUser(user);

        notificationDto = new NotificationDto();
        notificationDto.setUserId(1L);
        notificationDto.setContent("New notification");
    }

    // ─── GET /notifications/{id} ─────────────────────────────────────

    @Test
    void testGetNotification_success() throws Exception {
        when(notificationService.getNotification(101L)).thenReturn(notification);

        mockMvc.perform(get("/notifications/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(101))
                .andExpect(jsonPath("$.content").value("New notification"));
    }

    // ─── GET /notifications/viewall ──────────────────────────────────

    @Test
    void testGetAllNotifications_success() throws Exception {
        when(notificationService.getAllNotifications())
                .thenReturn(Arrays.asList(notification));

        mockMvc.perform(get("/notifications/viewall"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("New notification"));
    }

    // ─── POST /notifications/add ─────────────────────────────────────

    @Test
    void testAddNotification_success() throws Exception {
        when(notificationService.addNotification(any(NotificationDto.class)))
                .thenReturn(101L);

        mockMvc.perform(post("/notifications/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(notificationDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.msg")
                        .value("Notification added successfully with id "))
                .andExpect(jsonPath("$.id").value(101));
    }

    // ─── GET /notifications/user/{userId} ────────────────────────────

    @Test
    void testGetNotificationsByUserId_success() throws Exception {
        when(notificationService.getNotificationsByUserId(1L))
                .thenReturn(Arrays.asList(notification));

        mockMvc.perform(get("/notifications/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].content").value("New notification"));
    }

    // ─── GET /notifications/user/{userId}/count ──────────────────────

    @Test
    void testCountNotificationsByUserId_success() throws Exception {
        when(notificationService.countNotificationsByUserId(1L))
                .thenReturn(5L);

        mockMvc.perform(get("/notifications/user/1/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    // ─── PUT /notifications/{id} ─────────────────────────────────────

    @Test
    void testUpdateNotification_success() throws Exception {
        Notification updatedNotification = new Notification();
        updatedNotification.setNotificationId(101L);
        updatedNotification.setContent("Updated notification");
        updatedNotification.setUser(user);

        NotificationDto updateDto = new NotificationDto();
        updateDto.setContent("Updated notification");

        when(notificationService.updateNotification(eq(101L), any(NotificationDto.class)))
                .thenReturn(updatedNotification);

        mockMvc.perform(put("/notifications/101")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.notificationId").value(101))
                .andExpect(jsonPath("$.content").value("Updated notification"));
    }

    // ─── DELETE /notifications/{id} ──────────────────────────────────

    @Test
    void testDeleteNotification_success() throws Exception {
        doNothing().when(notificationService).deleteNotification(101L);

        mockMvc.perform(delete("/notifications/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.msg")
                        .value("Notification deleted successfully with id "))
                .andExpect(jsonPath("$.id").value(101));
    }
}