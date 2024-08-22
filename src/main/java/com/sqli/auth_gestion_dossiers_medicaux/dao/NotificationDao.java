package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDao extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdAndIsReadFalse(Long userId);
}
