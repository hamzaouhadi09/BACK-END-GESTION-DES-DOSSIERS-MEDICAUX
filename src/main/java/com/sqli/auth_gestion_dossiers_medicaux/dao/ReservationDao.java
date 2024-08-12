package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.Reservation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationDao extends CrudRepository<Reservation, Long> {
}
