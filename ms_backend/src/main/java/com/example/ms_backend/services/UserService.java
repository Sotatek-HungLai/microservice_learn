package com.example.ms_backend.services;

import com.example.ms_backend.dtos.UpdateUserBalanceDTO;
import com.example.ms_backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {


    private final UserRepository userRepository;
    public void updateUserBalance(UpdateUserBalanceDTO updateSellerBalanceDTO) {
        Integer checkUpdate = userRepository.updateUserBalance(updateSellerBalanceDTO.userId(), updateSellerBalanceDTO.balance());
        log.info("User balance updated: {}", checkUpdate);
    }
}
