package com.example.ms_user_service.dtos;

import com.example.ms_user_service.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;

    public UserDTO convertToDto(UserEntity user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public UserEntity convertToEntity(SignupRequestDTO requestDTO) {
        UserEntity userEntity = modelMapper.map(requestDTO, UserEntity.class);
        if (requestDTO.getPassword() != null) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        return userEntity;

    }


}
