package com.sqli.auth_gestion_dossiers_medicaux.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    // ajouter l age
    @Column(nullable = false)
    private String Age;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String name;

    @Column(nullable = false)
    private boolean Statut=false;

   // private String BU;
   @Column(nullable = false)
   private boolean archived=false;
    @Column(name = "business_title")
    private String businessTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
  // test commit
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Site site;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Plannification> plannifications;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disponibilite> disponibilites = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DemandeVisite> demandes = new ArrayList<>();

   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Archivage> archivages = new ArrayList<>();


}

