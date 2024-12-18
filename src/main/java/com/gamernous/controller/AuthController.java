package com.gamernous.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamernous.dto.LoginRequestDTO;
import com.gamernous.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO loginRequest) {
        return service.login(loginRequest.getUsername(), loginRequest.getPassword());
    }
}
