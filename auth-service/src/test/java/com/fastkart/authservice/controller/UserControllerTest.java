package com.fastkart.authservice.controller;

import com.fastkart.authservice.service.UserService;
import com.fastkart.commonlibrary.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUser() {
        String token = "Bearer some-token";
        UserDto userDto = new UserDto();
        userDto.setUserId(1);

        when(userService.getUser(token)).thenReturn(userDto);

        UserDto response = userController.getUser(token);

        assertEquals(userDto, response);
        verify(userService, times(1)).getUser(token);
    }
}
