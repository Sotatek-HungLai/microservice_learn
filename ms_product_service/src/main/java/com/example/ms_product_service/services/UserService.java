package com.example.ms_product_service.services;

import com.example.ms_product_service.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;

    public UserDTO getUserProfile(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String GET_PROFILE_API = "http://localhost:9899/api/profile";
        ResponseEntity<UserDTO> response = restTemplate.exchange(GET_PROFILE_API, HttpMethod.GET, entity, UserDTO.class);
        return response.getBody();
    }

    public UserDTO getUserProfile(Long id,String authorizationHeader){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        String GET_PROFILE_API = "http://localhost:9899/api/users/" + id;
        ResponseEntity<UserDTO> response = restTemplate.exchange(GET_PROFILE_API, HttpMethod.GET, entity, UserDTO.class);
        return response.getBody();
    }

}
