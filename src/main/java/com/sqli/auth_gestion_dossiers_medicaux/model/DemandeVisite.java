package com.sqli.auth_gestion_dossiers_medicaux.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DemandeVisite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String message;

    private String fichierJoint; // Le chemin du fichier stocké sur le serveur

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Le collaborateur qui fait la demande

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDemande = new Date(); // Date de la demande
}
