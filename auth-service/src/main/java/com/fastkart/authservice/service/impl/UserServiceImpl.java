package com.fastkart.authservice.service.impl;

import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.repository.UserRepository;
import com.fastkart.authservice.security.JwtHelper;
import com.fastkart.authservice.service.UserService;
import com.fastkart.commonlibrary.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final JwtHelper jwtHelper;
    private final UserRepository userRepository;

    @Override
    public UserDto getUser(String token) {
        token = token.substring(7);
        String username = jwtHelper.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username + "not found"));
        return new UserDto(user.getUserId(), user.getRole().getRoleName());
    }
}
