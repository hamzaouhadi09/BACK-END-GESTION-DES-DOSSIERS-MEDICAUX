package com.sqli.auth_gestion_dossiers_medicaux.controller;
import com.sqli.auth_gestion_dossiers_medicaux.model.DemandeVisite;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import com.sqli.auth_gestion_dossiers_medicaux.service.DemandeVisiteService;
import com.sqli.auth_gestion_dossiers_medicaux.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/demandes-visite")
public class DemandeVisiteController {

    @Autowired
    private DemandeVisiteService demandeVisiteService;

    @Autowired
    private UserService userService;
    @PreAuthorize("hasRole('COLLABORATEUR')  ")
    @PostMapping
    public ResponseEntity<?> creerDemandeVisite(
            @RequestParam("message") String message,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Authentication authentication) {
        try {
            // Récupérer l'utilisateur authentifié
            User user = userService.findByUsername(authentication.getName());

            // Créer et sauvegarder la demande de visite
            DemandeVisite demandeVisite = demandeVisiteService.createDemandeVisite(user, message, file);

            return ResponseEntity.ok(demandeVisite);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'enregistrement du fichier.");
        }
    }
}
