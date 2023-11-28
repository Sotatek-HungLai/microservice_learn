package com.example.ms_market.services;

import com.example.ms_market.dtos.UpdateUserBalanceRequestDTO;
import com.example.ms_market.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;

    private final String PROFILE_API = "http://localhost:9899/api/profile";
    public Long getUserIdFromContext() {
        return getUserContext().getId();
    }
    public UserDTO getUserContext() {
        return (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public UserDTO getUserProfile(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDTO> response = restTemplate.exchange(PROFILE_API, HttpMethod.GET, entity, UserDTO.class);
        return response.getBody();
    }

    public void updateUserBalance(Long userId, Double amount,String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        UpdateUserBalanceRequestDTO updateUserBalanceRequestDTO = new UpdateUserBalanceRequestDTO(userId,amount);
        HttpEntity<UpdateUserBalanceRequestDTO> entity = new HttpEntity<>(updateUserBalanceRequestDTO,headers);
        String UPDATE_BALANCE_PROFILE_API = PROFILE_API+"/balance";
        restTemplate.exchange(UPDATE_BALANCE_PROFILE_API, HttpMethod.PUT, entity, Void.class);
    }
}
