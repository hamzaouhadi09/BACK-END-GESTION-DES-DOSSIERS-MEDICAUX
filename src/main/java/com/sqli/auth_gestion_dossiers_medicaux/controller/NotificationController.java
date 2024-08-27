package com.sqli.auth_gestion_dossiers_medicaux.controller;

import com.sqli.auth_gestion_dossiers_medicaux.model.Notification;
import com.sqli.auth_gestion_dossiers_medicaux.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/unread")
    public List<Notification> getUnreadNotifications(Principal principal) {
        Long userId = Long.parseLong(principal.getName());
        return notificationService.getUnreadNotifications(userId);
    }
}