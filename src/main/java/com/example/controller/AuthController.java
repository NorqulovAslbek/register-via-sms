package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.LoginDTO;
import com.example.dto.ProfileDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/phone")
    public ResponseEntity<?> registration(@Valid @RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }

    @PostMapping("/verification")
    public ResponseEntity<?> verification(@Valid @RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.verification(dto));
    }

    @PostMapping("/login")
    @Operation(summary = "Api for login", description = "this api used for authorization")
    public ResponseEntity<ProfileDTO> login(@RequestBody LoginDTO login) {
        return ResponseEntity.ok(authService.auth(login));
    }


}
