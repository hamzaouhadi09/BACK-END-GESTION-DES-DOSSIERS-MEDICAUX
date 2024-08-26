package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.NotificationDao;
import com.sqli.auth_gestion_dossiers_medicaux.dao.UserDao;
import com.sqli.auth_gestion_dossiers_medicaux.model.Notification;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private NotificationDao notificationDao;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void createNotification(Long userId, String message) {
        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            System.out.println("L'utilisateur avec l'ID " + userId + " n'existe pas !");
            return;
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(message);
        notification.setCreatedAt(LocalDateTime.now());
        notification.setRead(false);
        notificationDao.save(notification);

        // Envoyer la notification en temps réel via WebSocket
        messagingTemplate.convertAndSendToUser(String.valueOf(user.getId()), "/queue/notify", notification);
    }

    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationDao.findByUserIdAndIsReadFalse(userId);
    }
}