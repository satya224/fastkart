package com.fastkart.authservice.controller;

import com.fastkart.authservice.model.dto.JwtResponse;
import com.fastkart.authservice.model.dto.UserAuthDto;
import com.fastkart.authservice.model.enums.RoleEnum;
import com.fastkart.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@SecurityRequirement(name = "security")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("signup")
    @ResponseStatus(HttpStatus.CREATED)
    public String signup(@RequestBody UserAuthDto userAuthDto, @RequestParam RoleEnum role) {
        log.info("Inside signup method of AuthController");
        return authService.signup(userAuthDto, role);
    }

    @PostMapping("login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public JwtResponse login(@RequestBody UserAuthDto userAuthDto) {
        log.info("Inside login method of AuthController");
        return authService.login(userAuthDto);
    }

}
