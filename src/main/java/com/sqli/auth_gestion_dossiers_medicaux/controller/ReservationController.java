package com.sqli.auth_gestion_dossiers_medicaux.controller;


import com.sqli.auth_gestion_dossiers_medicaux.dto.ReservationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Reservation;
import com.sqli.auth_gestion_dossiers_medicaux.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @PostMapping("/create")
    public ResponseEntity<Reservation> createReservation(@RequestBody ReservationDto reservationDto) {
        Reservation newReservation = reservationService.createReservation(reservationDto);
        return ResponseEntity.ok(newReservation);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('CHARGE_RH') or hasRole('MANAGER_RH')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
}
