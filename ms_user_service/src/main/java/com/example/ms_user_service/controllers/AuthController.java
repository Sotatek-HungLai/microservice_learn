package com.example.ms_user_service.controllers;

import com.example.ms_user_service.dtos.JwtGenResponseDTO;
import com.example.ms_user_service.dtos.SignInRequestDTO;
import com.example.ms_user_service.dtos.UserConverter;
import com.example.ms_user_service.dtos.UserDTO;
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

    private final UserConverter userConverter;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void signup(@RequestBody UserDTO userDTO) {
        authService.signup(userConverter.convertToEntity(userDTO));
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtGenResponseDTO> login(@RequestBody SignInRequestDTO userDTO) {
        JwtGenResponseDTO jwtGenResponseDTO = authService.login(userDTO.email(), userDTO.password());
        return ResponseEntity.ok().body(jwtGenResponseDTO);
    }


}
