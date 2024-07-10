package com.sqli.auth_gestion_dossiers_medicaux.controller;


import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.model.User;
import com.sqli.auth_gestion_dossiers_medicaux.service.UserManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class UserManagementController {

    @Autowired
    private UserManagementService userManagementService;

    @PreAuthorize("hasRole('MANAGER_RH')")
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = userManagementService.createUser(userDto);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('MANAGER_RH')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userManagementService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('MANAGER_RH')")
    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userManagementService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('MANAGER_RH')")
    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        User user = userManagementService.updateUser(id, userDto);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @PreAuthorize("hasRole('MANAGER_RH')")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userManagementService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
