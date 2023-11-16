package com.example.ms_user_service.dtos;

import lombok.Builder;

@Builder
public record JwtGenRequestDTO(String gmail, String password) {


}
