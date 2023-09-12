package com.fastkart.authservice.service;

import com.fastkart.authservice.model.dto.JwtResponse;
import com.fastkart.authservice.model.dto.UserAuthDto;
import com.fastkart.authservice.model.entity.Role;
import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.model.enums.RoleEnum;
import com.fastkart.authservice.repository.RoleRepository;
import com.fastkart.authservice.repository.UserRepository;
import com.fastkart.authservice.security.CustomUserDetailsService;
import com.fastkart.authservice.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtHelper jwtHelper;

    public String signup(UserAuthDto userAuthDto, RoleEnum roleEnum) {
        User user = new User();
        Role role = roleRepository.findByRoleName(roleEnum.name()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setUsername(userAuthDto.getUsername());
        user.setPassword(passwordEncoder.encode(userAuthDto.getPassword()));
        user.setRole(role);
        userRepository.save(user);
        return userAuthDto.getUsername() + " registered successfully";
    }

    public JwtResponse login(UserAuthDto userAuthDto) {
        doAuthenticate(userAuthDto.getUsername(), userAuthDto.getPassword());
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userAuthDto.getUsername());
        String token = jwtHelper.generateToken(userDetails);
        return new JwtResponse(token, userDetails.getUsername());
    }

    private void doAuthenticate(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
    }
}
