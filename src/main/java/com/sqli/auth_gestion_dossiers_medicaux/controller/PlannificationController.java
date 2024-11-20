package com.sqli.auth_gestion_dossiers_medicaux.controller;

import com.sqli.auth_gestion_dossiers_medicaux.dto.PlannificationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import com.sqli.auth_gestion_dossiers_medicaux.service.EmailService;
import com.sqli.auth_gestion_dossiers_medicaux.service.NotificationService;
import com.sqli.auth_gestion_dossiers_medicaux.service.PlannificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private EmailService emailService;

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @PostMapping("/create")
    public ResponseEntity<Plannification> createPlannification(@RequestBody PlannificationDto plannificationDto) {
        Plannification newPlannification = plannificationService.createPlannifications(plannificationDto);

        // Envoyer une notification à l'utilisateur
        User user = newPlannification.getUser();
        String message = "Bonjour " + user.getUsername() + ", votre rendez-vous pour la visite  aura lieu le "
                + newPlannification.getDate() + " à " + newPlannification.getTime() + ".";
        notificationService.createNotification(user.getId(), message);

        return ResponseEntity.ok(newPlannification);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @PostMapping("/api/sendEmail")
    public ResponseEntity<String> sendEmail(@RequestBody PlannificationDto request) {
        User user = request.getUser();
        if (user != null && isValidEmail(user.getEmail())) {
            String to = user.getEmail();
            String subject = "Confirmation de la réservation";

            // Nouveau corps de message personnalisé
            String body = String.format(
                    "Bonjour %s,<br><br>" +
                            "J'espère que vous allez bien !<br><br>" +
                            "Afin de veiller sur la santé et le bien-être de nos collaborateurs, nous organisons en collaboration avec le médecin de travail des RDV de visites médicales chaque lundi, " +
                            "de ce fait je vous invite à choisir votre créneau horaire via ce <a href=\"http://localhost:3000/choisir-creneau\">Lien</a> aujourd'hui avant 16h00.<br><br>" +
                            "PS : prière de choisir un seul créneau<br><br>" +
                            "Merci d'avance de votre retour.<br><br>" +
                            "Bien à vous.<br><br>" +
                            "Cordialement,<br>",
                    user.getUsername());
            try {
                emailService.sendEmail(to, subject, body);
                return ResponseEntity.ok("Email envoyé avec succès!");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Échec de l'envoi de l'email : " + e.getMessage());
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Utilisateur non spécifié ou email invalide");
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }


    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping
    public ResponseEntity<List<Plannification>> getAllPlannifications() {
        List<Plannification> plannifications = plannificationService.getAllPlannifications();
        return ResponseEntity.ok(plannifications);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping("/{id}")
    public ResponseEntity<Plannification> getPlannificationById(@PathVariable Long id) {
        Optional<Plannification> reservation = plannificationService.getPlannificationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlannification(@PathVariable Long id) {
        plannificationService.deletePlannification(id);
        return ResponseEntity.noContent().build();
    }
}
