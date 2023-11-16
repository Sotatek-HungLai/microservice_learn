package com.example.ms_user_service.services;

import com.example.ms_user_service.entities.UserEntity;
import com.example.ms_user_service.exceptions.UserNotFoundException;
import com.example.ms_user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void updateUserProfile(Long id, String address, String phone) {
        UserEntity foundUserEntity = findUserById(id);
        if (phone != null) {
            foundUserEntity.setPhone(phone);
        }
        if (address != null) {
            foundUserEntity.setAddress(address);
        }
        userRepository.save(foundUserEntity);
    }

    public void updateUserBalance(Long id, Double balance) {
        UserEntity foundUserEntity = findUserById(id);
        foundUserEntity.setBalance(balance);
        userRepository.save(foundUserEntity);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }
}
