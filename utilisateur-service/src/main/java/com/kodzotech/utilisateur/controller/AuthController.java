package com.kodzotech.utilisateur.controller;


import com.kodzotech.utilisateur.service.AuthService;
import com.kodzotech.utilisateur.dto.AuthentificationResponse;
import com.kodzotech.utilisateur.dto.LoginRequest;
import com.kodzotech.utilisateur.dto.RefreshTokenRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping("/signup")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void signup(@RequestBody RegisterRequest registerRequest) {
//        authService.signup(registerRequest);
//    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthentificationResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("refresh/token")
    @ResponseStatus(HttpStatus.OK)
    public AuthentificationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        authService.logout(refreshTokenRequest);
    }
}
