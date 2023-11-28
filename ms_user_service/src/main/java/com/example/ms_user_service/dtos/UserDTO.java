package com.example.ms_user_service.dtos;

import com.example.ms_user_service.entities.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String address;
    private String phone;
    private Role role;
    private Double balance;
}

