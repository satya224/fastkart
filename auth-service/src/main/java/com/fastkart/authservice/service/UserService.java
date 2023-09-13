package com.fastkart.authservice.service;

import com.fastkart.commonlibrary.dto.UserDto;

public interface UserService {
    UserDto getUser(String token);
}
