package com.gabrielf.padaria.controller;

import com.gabrielf.padaria.dto.AuthResponse;
import com.gabrielf.padaria.dto.LoginRequest;
import com.gabrielf.padaria.dto.RegistroRequest;
import com.gabrielf.padaria.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponse> registrar(@RequestBody @Valid RegistroRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED).body(authService.registrar(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest request) {

        return ResponseEntity.ok(authService.login(request));
    }
}
