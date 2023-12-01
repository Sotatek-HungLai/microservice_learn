package com.example.ms_user_service.controllers;

import com.example.ms_user_service.dtos.UdpateUserProfileRequestDTO;
import com.example.ms_user_service.dtos.UpdateUserBalanceRequestDTO;
import com.example.ms_user_service.dtos.UserDTO;
import com.example.ms_user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    @PreAuthorize("hasRole('ROLE_LOGGED_IN')")
    public ResponseEntity<UserDTO> getUserProfile() {
        return ResponseEntity.ok().body(userService.getUserProfile());
    }

    @PreAuthorize("hasRole('ROLE_LOGGED_IN')")
    @PatchMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserProfile(@RequestBody UdpateUserProfileRequestDTO userDTO) {
        userService.updateUserProfile(userDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserBalance(@PathVariable("id") Long id, @RequestBody UpdateUserBalanceRequestDTO balanceDTO) {
        userService.updateUserBalance(id, balanceDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserProfileById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userService.getUserProfileById(id));
    }

}
