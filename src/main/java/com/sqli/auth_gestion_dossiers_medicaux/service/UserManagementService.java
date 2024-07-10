package com.sqli.auth_gestion_dossiers_medicaux.service;

import com.sqli.auth_gestion_dossiers_medicaux.dao.UserDao;
import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UserManagementService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    public User createUser(UserDto userDto) {
        User user = userDto.getUserFromDto();
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        user.setRole(userDto.getRole());
        return userDao.save(user);
    }

    public List<User> getAllUsers() {
        return (List<User>) userDao.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userDao.findById(id);
    }

    public User updateUser(Long id, UserDto userDto) {
        Optional<User> existingUser = userDao.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDto.getUsername());
            user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
            user.setEmail(userDto.getEmail());
            user.setPhone(userDto.getPhone());
            user.setName(userDto.getName());
            user.setBusinessTitle(userDto.getBusinessTitle());
            user.setRole(userDto.getRole());
            return userDao.save(user);
        }
        return null;
    }

    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }

}
