package com.gamernous.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class AuthService {
    private final AuthenticationManager authManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    @Value("${jwt.secret}")
    private String jwtSecret;
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public AuthService(AuthenticationManager authManager, UserService userService, PasswordEncoder passwordEncoder) {
        this.authManager = authManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {
        UserDetails userdetails = userService.loadUserByUsername(username);

        if (userdetails == null || !passwordEncoder.matches(password, userdetails.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(username, password, userdetails.getAuthorities())
        );

        Key secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        String jwt = Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
            .signWith(secretKey, SignatureAlgorithm.HS512)
            .compact();

        return jwt;    
    }
}
