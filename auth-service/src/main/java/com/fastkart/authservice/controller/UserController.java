package com.fastkart.authservice.controller;

import com.fastkart.authservice.model.entity.User;
import com.fastkart.authservice.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("validate")
    public Integer getUserId(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) {
        return userService.getUserId(token);
    }
}
