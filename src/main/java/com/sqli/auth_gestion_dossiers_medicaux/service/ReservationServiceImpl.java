package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.ReservationDao;
import com.sqli.auth_gestion_dossiers_medicaux.dao.UserDao;
import com.sqli.auth_gestion_dossiers_medicaux.dto.ReservationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Reservation;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService{

    @Autowired
    private ReservationDao reservationDao;

    @Autowired
    private UserDao userDao;
    @Override
    public Reservation createReservation(ReservationDto reservationDto) {
        Optional<User> userOptional = userDao.findById(reservationDto.getCollaborateurId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Reservation reservation = reservationDto.getReservationFromDto(user);
            return reservationDao.save(reservation);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + reservationDto.getCollaborateurId());
        }
    }

    @Override
    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationDao.findAll();
    }

    @Override
    public Optional<Reservation> getReservationById(Long id) {
        return reservationDao.findById(id);
    }

    @Override
    public void deleteReservation(Long id) {
        reservationDao.deleteById(id);
    }
}
