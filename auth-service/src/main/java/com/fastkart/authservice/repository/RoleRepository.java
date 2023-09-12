package com.fastkart.authservice.repository;

import com.fastkart.authservice.model.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String role);

    boolean existsByRoleName(String role);
}
