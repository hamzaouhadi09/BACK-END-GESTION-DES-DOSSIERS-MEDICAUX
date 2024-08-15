package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;

import java.util.List;

public interface UserService {
    User save(UserDto user);
    List<User> findAll();
    User findByUsername(String username);
    User findByEmail(String email);

}
