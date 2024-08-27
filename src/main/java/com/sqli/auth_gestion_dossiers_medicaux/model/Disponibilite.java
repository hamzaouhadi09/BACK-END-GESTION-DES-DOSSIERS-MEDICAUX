package com.sqli.auth_gestion_dossiers_medicaux.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "disponibilites")
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek jour;  // Jour de la semaine

    @Column(nullable = false)
    private LocalTime heureDebut;  // Heure de début

    @Column(nullable = false)
    private LocalTime heureFin;  // Heure de fin

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Associé à l'utilisateur ayant le rôle "MEDECIN"

    // Getters and Setters
}
