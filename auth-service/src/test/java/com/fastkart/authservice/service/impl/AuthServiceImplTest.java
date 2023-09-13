package com.fastkart.authservice.service.impl;

import com.fastkart.authservice.model.dto.JwtResponse;
import com.fastkart.authservice.model.dto.UserAuthDto;
import com.fastkart.authservice.model.entity.Role;
import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.model.enums.RoleEnum;
import com.fastkart.authservice.repository.RoleRepository;
import com.fastkart.authservice.repository.UserRepository;
import com.fastkart.authservice.security.CustomUserDetailsService;
import com.fastkart.authservice.security.JwtHelper;
import com.fastkart.authservice.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private JwtHelper jwtHelper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSignup() {
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername("testuser");
        userAuthDto.setPassword("password");
        Role role = new Role();
        role.setRoleName("BUYER");

        when(roleRepository.findByRoleName(RoleEnum.BUYER.name())).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(userAuthDto.getPassword())).thenReturn("encodedPassword");

        String result = authService.signup(userAuthDto, RoleEnum.BUYER);

        assertEquals("testuser registered successfully", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogin() {
        UserAuthDto userAuthDto = new UserAuthDto();
        userAuthDto.setUsername("testuser");
        userAuthDto.setPassword("password");
        User user = new User();
        user.setUsername(userAuthDto.getUsername());
        user.setPassword("encodedPassword");

        when(userRepository.findByUsername(userAuthDto.getUsername())).thenReturn(Optional.of(user));
        when(jwtHelper.generateToken(any(UserDetails.class))).thenReturn("testToken");
        when(customUserDetailsService.loadUserByUsername(userAuthDto.getUsername())).thenReturn(user);

        JwtResponse response = authService.login(userAuthDto);

        assertEquals("testToken", response.getJwtToken());
        assertEquals("testuser", response.getUsername());
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
