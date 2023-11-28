package com.example.ms_user_service.controllers;

import com.example.ms_user_service.dtos.JwtGenResponseDTO;
import com.example.ms_user_service.dtos.SignInRequestDTO;
import com.example.ms_user_service.dtos.SignupRequestDTO;
import com.example.ms_user_service.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody SignupRequestDTO requestDTO) {
        authService.signup(requestDTO);
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtGenResponseDTO> signin(@RequestBody SignInRequestDTO requestDTO) {
        JwtGenResponseDTO jwtGenResponseDTO = authService.signin(requestDTO);
        return ResponseEntity.ok().body(jwtGenResponseDTO);
    }


}
