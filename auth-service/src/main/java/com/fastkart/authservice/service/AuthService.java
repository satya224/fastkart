package com.fastkart.authservice.service;

import com.fastkart.authservice.model.dto.JwtResponse;
import com.fastkart.authservice.model.dto.UserAuthDto;
import com.fastkart.authservice.model.enums.RoleEnum;

public interface AuthService {
    String signup(UserAuthDto userAuthDto, RoleEnum roleEnum);

    JwtResponse login(UserAuthDto userAuthDto);
}
