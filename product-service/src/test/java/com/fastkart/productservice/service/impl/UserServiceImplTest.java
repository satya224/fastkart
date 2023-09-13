package com.fastkart.productservice.service.impl;


import com.fastkart.commonlibrary.exception.FastKartException;
import com.fastkart.productservice.model.entity.User;
import com.fastkart.productservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetSeller() {
        // Mock the userRepository to return a user with a specific ID
        int userId = 1;
        User seller = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(seller));

        // Call the service method
        User result = userService.getSeller(userId);

        // Verify that the repository method was called and the result is not null
        verify(userRepository).findById(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetSellerWithInvalidId() {
        // Mock the userRepository to return an empty result (user not found)
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(FastKartException.class, () -> userService.getSeller(userId));
    }

    @Test
    public void testGetBuyer() {
        // Mock the userRepository to return a user with a specific ID
        int userId = 1;
        User buyer = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(buyer));

        // Call the service method
        User result = userService.getBuyer(userId);

        // Verify that the repository method was called and the result is not null
        verify(userRepository).findById(userId);
        assertNotNull(result);
    }

    @Test
    public void testGetBuyerWithInvalidId() {
        // Mock the userRepository to return an empty result (user not found)
        int userId = 1;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(FastKartException.class, () -> userService.getBuyer(userId));
    }

}