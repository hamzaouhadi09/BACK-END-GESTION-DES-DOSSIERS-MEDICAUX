package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.Plannification;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlannificationDao extends CrudRepository<Plannification, Long> {
}
