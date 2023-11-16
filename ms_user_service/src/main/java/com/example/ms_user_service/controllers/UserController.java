package com.example.ms_user_service.controllers;

import com.example.ms_user_service.dtos.UdpateUserProfileRequestDTO;
import com.example.ms_user_service.dtos.UpdateUserBalanceRequestDTO;
import com.example.ms_user_service.dtos.UserConverter;
import com.example.ms_user_service.dtos.UserDTO;
import com.example.ms_user_service.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final UserConverter userConverter;

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserDTO> getUserProfile(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(userConverter.convertToDto(userService.findUserById(id)));
    }

    @PatchMapping("/{id}/profile")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserProfile(@PathVariable("id") Long id, @RequestBody UdpateUserProfileRequestDTO userDTO) {
        userService.updateUserProfile(id, userDTO.address(), userDTO.phone());
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateUserBalance(@PathVariable("id") Long id, @RequestBody UpdateUserBalanceRequestDTO balanceDTO) {
        userService.updateUserBalance(id, balanceDTO.balance());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }


}
