package com.fastkart.authservice.service.impl;

import com.fastkart.authservice.model.entity.Role;
import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.repository.UserRepository;
import com.fastkart.authservice.security.JwtHelper;
import com.fastkart.authservice.service.impl.UserServiceImpl;
import com.fastkart.commonlibrary.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser() {
        String token = "Bearer testToken";
        String username = "testuser";
        User user = new User();
        user.setUserId(1);
        user.setUsername(username);
        user.setRole(new Role(1, "BUYER"));

        when(jwtHelper.getUsernameFromToken(token.substring(7))).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDto userDto = userService.getUser(token);

        assertEquals(1, userDto.getUserId());
        assertEquals("BUYER", userDto.getRole());
    }
}
