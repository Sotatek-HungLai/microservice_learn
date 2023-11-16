package com.example.ms_user_service.dtos;

import com.example.ms_user_service.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserConverter {
    private final ModelMapper modelMapper;

    public UserDTO convertToDto(UserEntity user) {
        UserDTO userDTO = this.modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    public UserEntity convertToEntity(UserDTO user) {
        return modelMapper.map(user, UserEntity.class);
    }
}
