package com.sqli.auth_gestion_dossiers_medicaux.controller;


import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.service.UserService;
import com.sqli.auth_gestion_dossiers_medicaux.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.GrantedAuthority;
import java.util.HashMap;
import java.util.Map;


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

  // @RequestMapping(value="/register", method = RequestMethod.POST)
  //public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {
   //   return ResponseEntity.ok(userService.save(userDto));
  // }

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

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/currentRole", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getCurrentUserRole(Authentication authentication) {
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");
        Map<String, String> response = new HashMap<>();
        response.put("role", role);
        return ResponseEntity.ok(response);
        //add comment
    }
    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/currentUser", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getCurrentUserDetails(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("UNKNOWN");

        Map<String, String> response = new HashMap<>();
        response.put("username", username);
        response.put("role", role);

        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasRole('MANAGER_RH')")
    @GetMapping("/summary")
    public Map<String, Long> getUserSummary() {
        long totalUsers = userService.getTotalUsers();
        Map<String, Long> response = new HashMap<>();
        response.put("totalUsers", totalUsers);
        return response; // Returns a JSON with the total users count
    }


}
