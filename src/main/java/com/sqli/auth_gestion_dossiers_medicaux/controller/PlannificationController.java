package com.sqli.auth_gestion_dossiers_medicaux.controller;


import com.sqli.auth_gestion_dossiers_medicaux.dto.PlannificationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import com.sqli.auth_gestion_dossiers_medicaux.service.NotificationService;
import com.sqli.auth_gestion_dossiers_medicaux.service.PlannificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/plannification")
public class PlannificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private PlannificationService plannificationService;

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @PostMapping("/create")
    public ResponseEntity<Plannification> createReservation(@RequestBody PlannificationDto plannificationDto) {
        Plannification newPlannification = plannificationService.createPlannifications(plannificationDto);

        // Envoyer une notification à l'utilisateur
        User user = newPlannification.getUser();
        String message = "Bonjour " + user.getUsername() + ", votre rendez-vous aura lieu le "
                + newPlannification.getDate() + " à " + newPlannification.getTime() + ".";
        notificationService.createNotification(user.getId(), message);

        return ResponseEntity.ok(newPlannification);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping
    public ResponseEntity<List<Plannification>> getAllReservations() {
        List<Plannification> plannifications = plannificationService.getAllPlannifications();
        return ResponseEntity.ok(plannifications);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping("/{id}")
    public ResponseEntity<Plannification> getReservationById(@PathVariable Long id) {
        Optional<Plannification> reservation = plannificationService.getPlannificationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        plannificationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
