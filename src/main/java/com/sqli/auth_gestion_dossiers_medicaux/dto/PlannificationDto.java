package com.sqli.auth_gestion_dossiers_medicaux.dto;


import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlannificationDto {
    private LocalDate date;
    private LocalTime time;
    private String type;
    private Long collaborateurId;  // ID of the collaborateur (User) making the reservation
    private User user;
    public Plannification getReservationFromDto(User user) {
        Plannification plannification = new Plannification();
        plannification.setDate(date);
        plannification.setTime(time);
        plannification.setType(type);
        plannification.setUser(user);
        return plannification;
    }
}
