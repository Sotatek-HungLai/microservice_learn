package com.example.ms_auth_service.controllers;

import com.example.ms_auth_service.dtos.JwtGenRequestDTO;
import com.example.ms_auth_service.dtos.JwtGenResponseDTO;
import com.example.ms_auth_service.dtos.JwtVerifyResponseDTO;
import com.example.ms_auth_service.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "bearer", name = "Authorization")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @Operation(summary = "Generate JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generate JWT Token"),
    })
    @PostMapping("/generate-jwt")
    public ResponseEntity<JwtGenResponseDTO> getToken(@RequestBody JwtGenRequestDTO jwtGenRequestDTO) {
        String token = service.generateToken(jwtGenRequestDTO.username());
        return ResponseEntity.ok().body(new JwtGenResponseDTO(token));
    }

    @SecurityRequirement(name = AUTHORIZATION)
    @Operation(summary = "Verify JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verify JWT Token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping("/verify-jwt")
    public ResponseEntity<JwtVerifyResponseDTO> validateToken(@RequestHeader("Authorization") String token) {
        System.out.println(token);
        String username = service.validateToken(token);
        return ResponseEntity.ok().body(
                new JwtVerifyResponseDTO(username)
        );
    }
}

