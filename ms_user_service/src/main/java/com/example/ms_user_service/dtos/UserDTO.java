package com.example.ms_user_service.dtos;

import com.example.ms_user_service.entities.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String address;
    private String phone;
    private String password;
    private Role role;
    private Double balance;
}

