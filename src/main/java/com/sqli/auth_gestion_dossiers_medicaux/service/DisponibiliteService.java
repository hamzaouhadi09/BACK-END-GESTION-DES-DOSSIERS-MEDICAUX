package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.DisponibiliteDao;
import com.sqli.auth_gestion_dossiers_medicaux.model.Disponibilite;
import com.sqli.auth_gestion_dossiers_medicaux.model.Role;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DisponibiliteService {

    @Autowired
    private DisponibiliteDao disponibiliteDao;

    @Autowired
    private UserManagementService userManagementService;  // Utilisé pour récupérer les médecins

    public List<Disponibilite> getDisponibilitesByMedecin(Long medecinId) {
        return disponibiliteDao.findByUserId(medecinId);
    }

    public Disponibilite createDisponibilite(Long medecinId, Disponibilite disponibilite) {
        Optional<User> medecinOpt = userManagementService.getUserById(medecinId);
        if (medecinOpt.isPresent() && medecinOpt.get().getRole() == Role.MEDECIN) {
            disponibilite.setUser(medecinOpt.get());
            return disponibiliteDao.save(disponibilite);
        }
        throw new IllegalArgumentException("L'utilisateur n'est pas un médecin ou n'existe pas.");
    }

    public Disponibilite updateDisponibilite(Long id, Disponibilite updatedDisponibilite) {
        Optional<Disponibilite> existingDisponibilite = disponibiliteDao.findById(id);
        if (existingDisponibilite.isPresent()) {
            Disponibilite disponibilite = existingDisponibilite.get();
            disponibilite.setJour(updatedDisponibilite.getJour());
            disponibilite.setHeureDebut(updatedDisponibilite.getHeureDebut());
            disponibilite.setHeureFin(updatedDisponibilite.getHeureFin());
            return disponibiliteDao.save(disponibilite);
        }
        throw new IllegalArgumentException("Disponibilité non trouvée.");
    }

    public void deleteDisponibilite(Long id) {
        disponibiliteDao.deleteById(id);
    }
}
