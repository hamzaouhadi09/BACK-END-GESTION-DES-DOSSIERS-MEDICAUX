package com.sqli.auth_gestion_dossiers_medicaux;

import com.sqli.auth_gestion_dossiers_medicaux.controller.UserController;
import com.sqli.auth_gestion_dossiers_medicaux.dto.UserDto;
import com.sqli.auth_gestion_dossiers_medicaux.service.UserService;
import com.sqli.auth_gestion_dossiers_medicaux.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {
    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAuthenticationToken() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser");
        userDto.setPassword("testpassword");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtTokenUtil.generateToken(userDetails)).thenReturn("testtoken");

        ResponseEntity<?> response = userController.createAuthenticationToken(userDto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("testtoken", response.getBody());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(jwtTokenUtil, times(1)).generateToken(userDetails);
    }

    @Test
    public void testColPing() {
        String response = userController.colPing();
        assertEquals("Any Collaborateur Can Read This", response);
    }

    @Test
    public void testMedPing() {
        String response = userController.medPing();
        assertEquals("Any MEDECIN Can Read This", response);
    }

    @Test
    public void testChargeRHPing() {
        String response = userController.ChargeRHPing();
        assertEquals("Any CHARGE_RH Can Read This", response);
    }

    @Test
    public void testManagerRHPing() {
        String response = userController.managerRHPing();
        assertEquals("Any MANAGER_RH Can Read This", response);
    }
}
