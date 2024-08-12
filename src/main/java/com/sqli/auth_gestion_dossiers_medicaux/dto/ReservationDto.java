package com.sqli.auth_gestion_dossiers_medicaux.dto;


import com.sqli.auth_gestion_dossiers_medicaux.model.Reservation;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDto {
    private LocalDate date;
    private LocalTime time;
    private String type;
    private Long collaborateurId;  // ID of the collaborateur (User) making the reservation

    public Reservation getReservationFromDto(User user) {
        Reservation reservation = new Reservation();
        reservation.setDate(date);
        reservation.setTime(time);
        reservation.setType(type);
        reservation.setUser(user);
        return reservation;
    }
}
