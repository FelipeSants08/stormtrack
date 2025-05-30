package com.fiap.stormtrack.controller;

import com.fiap.stormtrack.model.Credentials;
import com.fiap.stormtrack.model.Token;
import com.fiap.stormtrack.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Token login(@RequestBody @Valid Credentials credentials) {
        log.info("Logando como: " + credentials);
        return authService.login(credentials);
    }
}
