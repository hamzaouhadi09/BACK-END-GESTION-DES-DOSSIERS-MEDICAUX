package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DisponibiliteDao extends JpaRepository<Disponibilite, Long> {
    List<Disponibilite> findByUserId(Long userId);  // Récupérer les disponibilités d’un médecin spécifique
}
