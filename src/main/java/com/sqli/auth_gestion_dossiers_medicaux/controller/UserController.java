package com.sqli.auth_gestion_dossiers_medicaux.controller;


import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.service.UserService;
import com.sqli.auth_gestion_dossiers_medicaux.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value="/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserDto userDto) throws Exception {
        authenticate(userDto.getUsername(), userDto.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getUsername());
        final String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(token);
    }

//    @RequestMapping(value="/register", method = RequestMethod.POST)
//    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {
//        return ResponseEntity.ok(userService.save(userDto));
//    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @PreAuthorize("hasRole('COLLABORATEUR')")
    @RequestMapping(value="/collaborateur", method = RequestMethod.GET)
    public String colPing(){
        return "Any Collaborateur Can Read This";
    }

    @PreAuthorize("hasRole('MEDECIN')")
    @RequestMapping(value="/medecin", method = RequestMethod.GET)
    public String medPing(){
        return "Any MEDECIN Can Read This";
    }

    @PreAuthorize("hasRole('CHARGE_RH')")
    @RequestMapping(value="/chargeRH", method = RequestMethod.GET)
    public String ChargeRHPing(){
        return "Any CHARGE_RH Can Read This";
    }

    @PreAuthorize("hasRole('MANAGER_RH')")
    @RequestMapping(value="/managerRH", method = RequestMethod.GET)
    public String managerRHPing(){
        return "Any MANAGER_RH Can Read This";
    }

}
