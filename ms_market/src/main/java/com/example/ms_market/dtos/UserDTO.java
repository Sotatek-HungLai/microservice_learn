package com.example.ms_market.dtos;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class UserDTO {
    private Long id;
    private String email;
    private String address;
    private String phone;
    private Role role;
    private Double balance;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }
}

enum Role {
    ADMIN, MEMBER
}

