package com.example.ms_user_service.services;

import com.example.ms_user_service.dtos.UdpateUserProfileRequestDTO;
import com.example.ms_user_service.dtos.UpdateUserBalanceRequestDTO;
import com.example.ms_user_service.dtos.UserConverter;
import com.example.ms_user_service.dtos.UserDTO;
import com.example.ms_user_service.entities.UserEntity;
import com.example.ms_user_service.exceptions.UserNotFoundException;
import com.example.ms_user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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

    public void updateUserBalance(Long id, UpdateUserBalanceRequestDTO requestDTO) {
        UserEntity foundUserEntity = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.udpateBalance(foundUserEntity.getId(), requestDTO.balance());
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

    public void updateUserBalanceInTransaction(UpdateUserBalanceRequestDTO balanceDTO) {
        UserEntity foundUserEntity = userRepository.findById(balanceDTO.sellerId()).orElseThrow(UserNotFoundException::new);
        userRepository.udpateBalance(foundUserEntity.getId(), balanceDTO.balance());
        userRepository.udpateBalance(getUserProfileEntity().getId(), -balanceDTO.balance());
    }
}
