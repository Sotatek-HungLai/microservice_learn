package com.example.ms_product_service.services;

import com.example.ms_product_service.dtos.AddProductToUserDTO;
import com.example.ms_product_service.dtos.UserDTO;
import com.example.ms_product_service.entities.ProductEntity;
import com.example.ms_product_service.entities.ProductUser;
import com.example.ms_product_service.exceptions.ProductNotFoundException;
import com.example.ms_product_service.exceptions.ProductUserNotFoundException;
import com.example.ms_product_service.repositories.ProductRepository;
import com.example.ms_product_service.repositories.ProductUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductUserService {

    private final ProductRepository productRepository;

    private final UserService userService;

    private final ProductUserRepository productUserRepository;

    public void addProductToUser(AddProductToUserDTO addProductToUserDTO, String authorizationHeader) {
        ProductEntity productEntity = productRepository.findById(addProductToUserDTO.productId()).orElseThrow(ProductNotFoundException::new);
        UserDTO userDTO = userService.getUserProfile(addProductToUserDTO.userId(), authorizationHeader);
        log.info("userDTO : {}", userDTO.toString());
        Optional<ProductUser> productUserOptional = productUserRepository.findByUserIdAndProductId(userDTO.getId(), addProductToUserDTO.productId());
        if(productUserOptional.isPresent()){
            ProductUser foundProductUser = productUserOptional.get();
            Integer checkUpdate = productUserRepository.updateAmount(foundProductUser.getUserId(),foundProductUser.getProduct().getId(), addProductToUserDTO.amount());
            log.info("updatedProductUser : {}", checkUpdate);
        }
        else{
            ProductUser savedProductUser = productUserRepository.save(
                    ProductUser.builder()
                            .product(productEntity)
                            .userId(userDTO.getId())
                            .amount(addProductToUserDTO.amount())
                            .build()
            );
            log.debug("savedProductUser : {}", savedProductUser);
        }
    }
}
