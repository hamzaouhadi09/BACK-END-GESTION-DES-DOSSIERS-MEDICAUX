package com.sqli.auth_gestion_dossiers_medicaux.controller;

import com.sqli.auth_gestion_dossiers_medicaux.model.Disponibilite;
import com.sqli.auth_gestion_dossiers_medicaux.service.DisponibiliteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/disponibilites")
public class DisponibiliteController {

    @Autowired
    private DisponibiliteService disponibiliteService;
    @PreAuthorize("hasRole('MANAGER_RH')")
    @GetMapping("/medecin/{medecinId}")
    public ResponseEntity<List<Disponibilite>> getDisponibilitesByMedecin(@PathVariable Long medecinId) {
        return ResponseEntity.ok(disponibiliteService.getDisponibilitesByMedecin(medecinId));
    }
    @PreAuthorize("hasRole('MANAGER_RH')")
    @PostMapping("/medecin/{medecinId}")
    public ResponseEntity<Disponibilite> createDisponibilite(@PathVariable Long medecinId, @RequestBody Disponibilite disponibilite) {
        return ResponseEntity.ok(disponibiliteService.createDisponibilite(medecinId, disponibilite));
    }
    @PreAuthorize("hasRole('MANAGER_RH')")
    @PutMapping("/{id}")
    public ResponseEntity<Disponibilite> updateDisponibilite(@PathVariable Long id, @RequestBody Disponibilite disponibilite) {
        return ResponseEntity.ok(disponibiliteService.updateDisponibilite(id, disponibilite));
    }
    @PreAuthorize("hasRole('MANAGER_RH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisponibilite(@PathVariable Long id) {
        disponibiliteService.deleteDisponibilite(id);
        return ResponseEntity.noContent().build();
    }
}
