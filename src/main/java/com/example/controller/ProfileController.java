package com.example.controller;



import com.example.dto.CreateProfileDTO;
import com.example.service.ProfileService;
import com.example.util.SpringSecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Profile Api list", description = "Api list for Profile")
@Slf4j
@RestController
@RequestMapping("/profile")
@SecurityRequirement(name = "bearerAuth")
public class ProfileController {
    @Autowired
    private ProfileService profileService;

    @Operation(summary = "Api for create", description = "this api used for create profile")
    @PostMapping("/adm")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody CreateProfileDTO dto) {
        log.info("create admin{}", dto.getPhone());
        return ResponseEntity.ok(profileService.create(dto));
    }

}

