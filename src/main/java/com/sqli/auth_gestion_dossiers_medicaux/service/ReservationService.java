package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dto.ReservationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Reservation createReservation(ReservationDto reservationDto);
    List<Reservation> getAllReservations();
    Optional<Reservation> getReservationById(Long id);
    void deleteReservation(Long id);
}
