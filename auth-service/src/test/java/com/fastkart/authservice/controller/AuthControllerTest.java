package com.fastkart.authservice.controller;

import com.fastkart.authservice.model.dto.JwtResponse;
import com.fastkart.authservice.model.dto.UserAuthDto;
import com.fastkart.authservice.model.enums.RoleEnum;
import com.fastkart.authservice.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignup() {
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername("testuser");
        RoleEnum role = RoleEnum.BUYER;

        when(authServiceImpl.signup(userAuthDto, role)).thenReturn("User signed up successfully");

        String response = authController.signup(userAuthDto, role);

        assertEquals("User signed up successfully", response);
        verify(authServiceImpl, times(1)).signup(userAuthDto, role);
    }

    @Test
    public void testLogin() {
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername("testuser");

        JwtResponse jwtResponse = new JwtResponse("token", "testuser");

        when(authServiceImpl.login(userAuthDto)).thenReturn(jwtResponse);

        JwtResponse response = authController.login(userAuthDto);

        assertEquals(jwtResponse, response);
        verify(authServiceImpl, times(1)).login(userAuthDto);
    }
}
