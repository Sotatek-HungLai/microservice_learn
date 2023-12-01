package com.example.ms_user_service.services;

import com.example.ms_user_service.dtos.*;
import com.example.ms_user_service.entities.UserEntity;
import com.example.ms_user_service.exceptions.UserNotFoundException;
import com.example.ms_user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    public void updateUserProfile(UdpateUserProfileRequestDTO userDTO) {
        UserEntity foundUserEntity = getUserProfileEntity();
        if (userDTO.phone() != null) {
            foundUserEntity.setPhone(userDTO.phone());
        }
        if (userDTO.address() != null) {
            foundUserEntity.setAddress(userDTO.address());
        }
        userRepository.save(foundUserEntity);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public UserDTO getUserProfileById(Long id) {
        return userConverter.convertToDto(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    public UserDTO getUserProfile() {
        return userConverter.convertToDto(getUserProfileEntity());
    }

    private UserEntity getUserProfileEntity() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findUserByEmail(email);
    }

    public void updateUserBalance(Long id, UpdateUserBalanceRequestDTO balanceDTO) {
        Integer checkUpdate = userRepository.udpateBalance(id, balanceDTO.balance());
        log.info("User balance updated: {}", checkUpdate);
    }
}
