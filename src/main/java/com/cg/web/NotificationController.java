package com.cg.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.cg.dto.NotificationDto;
import com.cg.dto.SuccessMessageDto;
import com.cg.entity.Notification;
import com.cg.exception.ValidationException;
import com.cg.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/{id}")
    public Notification getNotification(@PathVariable Long id) {
        return notificationService.getNotification(id);
    }

    @GetMapping("/viewall")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public SuccessMessageDto addNotification(@Valid @RequestBody NotificationDto dto, BindingResult br) {

        if (br.hasErrors()) {
            throw new ValidationException(br.getFieldErrors());
        }

        Long nid = notificationService.addNotification(dto);

        return new SuccessMessageDto("Notification added successfully with id ", nid);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsByUserId(@PathVariable Long userId) {
        return notificationService.getNotificationsByUserId(userId);
    }
    
    @GetMapping("/user/{userId}/count")
    public Long countNotificationsByUserId(@PathVariable Long userId) {
        return notificationService.countNotificationsByUserId(userId);
    }

    @PutMapping("/{id}")
    public Notification updateNotification(@PathVariable Long id,
                                           @RequestBody NotificationDto dto) {
        return notificationService.updateNotification(id, dto);
    }

    @DeleteMapping("/{id}")
    public SuccessMessageDto deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return new SuccessMessageDto("Notification deleted successfully with id ", id);
    }
}