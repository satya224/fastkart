package com.fastkart.authservice.service;

import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.repository.UserRepository;
import com.fastkart.authservice.security.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    public Integer getUserId(String token) {
        token = token.substring(7);
        String username = this.jwtHelper.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return user.getUserId();
    }
}
