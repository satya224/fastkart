package com.fastkart.authservice.startup;

import com.fastkart.authservice.model.entity.Role;
import com.fastkart.authservice.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        if (!roleRepository.existsByRoleName("BUYER")) {
            Role buyerRole = new Role();
            buyerRole.setRoleName("BUYER");
            roleRepository.save(buyerRole);
        }

        if (!roleRepository.existsByRoleName("Seller")) {
            Role sellerRole = new Role();
            sellerRole.setRoleName("SELLER");
            roleRepository.save(sellerRole);
        }
    }
}
