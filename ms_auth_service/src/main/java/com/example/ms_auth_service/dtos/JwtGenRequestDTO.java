package com.example.ms_auth_service.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JwtGenRequestDTO{
    @NotNull
    private String gmail;
    @NotNull
    private String password;

}
