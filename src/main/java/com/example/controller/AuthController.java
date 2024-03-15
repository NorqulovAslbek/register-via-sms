package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.RegistrationDTO;
import com.example.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
