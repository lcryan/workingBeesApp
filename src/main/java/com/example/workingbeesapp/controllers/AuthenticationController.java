package com.example.workingbeesapp.controllers;

import com.example.workingbeesapp.dtos.AuthenticationDto;
import com.example.workingbeesapp.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authentication")

public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthenticationController(AuthenticationManager man, JwtService jwtService) {
        this.authenticationManager = man;
        this.jwtService = jwtService;
    }

    @PostMapping
    public ResponseEntity<Object> signIn(@Valid @RequestBody AuthenticationDto authenticationDto) {
        UsernamePasswordAuthenticationToken up =
                new UsernamePasswordAuthenticationToken(authenticationDto.getUsername(), authenticationDto.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(up);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            return ResponseEntity.ok()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .body("A token has been generated.");
        } catch (AuthenticationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
}
