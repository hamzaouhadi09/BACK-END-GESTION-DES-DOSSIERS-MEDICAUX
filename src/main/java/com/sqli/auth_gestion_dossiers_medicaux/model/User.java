package com.sqli.auth_gestion_dossiers_medicaux.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String name;

    @Column(name = "business_title")
    private String businessTitle;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
  // test commit

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Reservation> rservations;
    @Column(nullable = false)
    private boolean archived=false;


}
