package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.PlannificationDao;
import com.sqli.auth_gestion_dossiers_medicaux.dao.UserDao;
import com.sqli.auth_gestion_dossiers_medicaux.dto.PlannificationDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlannificationServiceImpl implements PlannificationService {

    @Autowired
    private PlannificationDao plannificationDao;

    @Autowired
    private UserDao userDao;
    @Override
    public Plannification createPlannifications(PlannificationDto plannificationDto) {
        Optional<User> userOptional = userDao.findById(plannificationDto.getCollaborateurId());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Plannification plannification = plannificationDto.getReservationFromDto(user);
            return plannificationDao.save(plannification);
        } else {
            throw new IllegalArgumentException("User not found with ID: " + plannificationDto.getCollaborateurId());
        }
    }

    @Override
    public List<Plannification> getAllPlannifications() {
        return (List<Plannification>) plannificationDao.findAll();
    }

    @Override
    public Optional<Plannification> getPlannificationById(Long id) {
        return plannificationDao.findById(id);
    }

    @Override
    public void deleteReservation(Long id) {
        plannificationDao.deleteById(id);
    }
}
