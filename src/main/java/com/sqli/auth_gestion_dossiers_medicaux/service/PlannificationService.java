package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dto.PlannificationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;

import java.util.List;
import java.util.Optional;

public interface PlannificationService {

    Plannification createPlannifications(PlannificationDto plannificationDto);
    List<Plannification> getAllPlannifications();
    Optional<Plannification> getPlannificationById(Long id);
    void deleteReservation(Long id);
}
