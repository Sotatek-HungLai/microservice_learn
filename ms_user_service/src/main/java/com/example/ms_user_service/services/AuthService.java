package com.example.ms_user_service.services;

import com.example.ms_user_service.dtos.JwtGenRequestDTO;
import com.example.ms_user_service.dtos.JwtGenResponseDTO;
import com.example.ms_user_service.dtos.JwtVerifyResponseDTO;
import com.example.ms_user_service.entities.UserEntity;
import com.example.ms_user_service.exceptions.IncorrectPasswordException;
import com.example.ms_user_service.exceptions.UserNotFoundException;
import com.example.ms_user_service.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final String AUTH_SERVICE_API = "http://localhost:9898/api/auth";

    private JwtGenResponseDTO generateToken(JwtGenRequestDTO jwtGenRequestDTO) {
        HttpEntity<JwtGenRequestDTO> request =
                new HttpEntity<>(jwtGenRequestDTO);
        ResponseEntity<JwtGenResponseDTO> responseEntity = restTemplate.postForEntity(AUTH_SERVICE_API + "/generate-jwt", request, JwtGenResponseDTO.class);
        return responseEntity.getBody();
    }


    public JwtVerifyResponseDTO validateToken(String authorizationHeader) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", authorizationHeader);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<JwtVerifyResponseDTO> responseEntity = restTemplate.postForEntity(AUTH_SERVICE_API + "/verify-jwt", requestEntity, JwtVerifyResponseDTO.class);
        log.info("JwtVerifyResponseDTO : {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public void signup(UserEntity userEntity) {
        userRepository.save(userEntity);

    }

    public JwtGenResponseDTO login(String email, String password) {
        UserEntity foundUserEntity = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if (!foundUserEntity.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }
        JwtGenRequestDTO jwtGenRequestDTO = JwtGenRequestDTO.builder().gmail(foundUserEntity.getEmail()).password(foundUserEntity.getPassword()).build();
        return generateToken(jwtGenRequestDTO);
    }
}
