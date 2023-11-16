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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "bearer", name = "Authorization")
@Validated
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
    public ResponseEntity<JwtGenResponseDTO> getToken(@RequestBody @Valid JwtGenRequestDTO jwtGenRequestDTO) {
        log.info("JwtGenRequestDTO: {}", jwtGenRequestDTO.toString());
        String token = service.generateToken(jwtGenRequestDTO.getGmail());
        log.info("Generated token: {}", token);
        return ResponseEntity.ok().body(new JwtGenResponseDTO(token));
    }

    @SecurityRequirement(name = AUTHORIZATION)
    @Operation(summary = "Verify JWT Token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Verify JWT Token"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
    })
    @PostMapping("/verify-jwt")
    public ResponseEntity<JwtVerifyResponseDTO> validateToken(@RequestHeader("Authorization") String authHeaderValue) {
        log.info("Validate authHeaderValue: {}", authHeaderValue);
        String gmail = service.validateToken(authHeaderValue);
        log.info("Validated gmail: {}", gmail);
        return ResponseEntity.ok().body(
                new JwtVerifyResponseDTO(gmail)
        );
    }
}

