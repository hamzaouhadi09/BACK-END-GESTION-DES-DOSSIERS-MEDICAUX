package com.sqli.auth_gestion_dossiers_medicaux.dao;

import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

}
