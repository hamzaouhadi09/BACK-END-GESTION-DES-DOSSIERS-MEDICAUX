package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.Archivage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivageDao extends CrudRepository<Archivage,Long> {
    void deleteByUserId(Long id);
}
