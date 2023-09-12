package com.fastkart.authservice.controller;

import com.fastkart.authservice.service.UserService;
import com.fastkart.commonlibrary.dto.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("validate")
    public UserDto getUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        log.info("Inside getUser method of UserController");
        return userService.getUser(token);
    }
}
