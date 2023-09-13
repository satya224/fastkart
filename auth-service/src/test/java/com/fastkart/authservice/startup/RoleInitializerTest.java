package com.fastkart.authservice.startup;

import com.fastkart.authservice.model.entity.Role;
import com.fastkart.authservice.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class RoleInitializerTest {

    @InjectMocks
    private RoleInitializer roleInitializer;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun() {
        // Create a mock Role object for "BUYER" role
        Role buyerRole = new Role();
        buyerRole.setRoleName("BUYER");

        // Create a mock Role object for "SELLER" role
        Role sellerRole = new Role();
        sellerRole.setRoleName("SELLER");

        // Mock the roleRepository.existsByRoleName method to return false for both roles
        when(roleRepository.existsByRoleName("BUYER")).thenReturn(false);
        when(roleRepository.existsByRoleName("SELLER")).thenReturn(false);

        // Mock the roleRepository.save method to return the mock roles
        when(roleRepository.save(any(Role.class))).thenReturn(buyerRole, sellerRole);

        // Call the run method
        roleInitializer.run();

        // Verify that existsByRoleName was called for both roles
        verify(roleRepository, times(2)).existsByRoleName(anyString());

        // Verify that save was called for both roles
        verify(roleRepository, times(2)).save(any(Role.class));
    }
}
