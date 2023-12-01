package com.example.ms_backend.services;

import com.example.ms_backend.dtos.UpdateProductAmountDTO;
import com.example.ms_backend.entities.products.ProductEntity;
import com.example.ms_backend.entities.products.ProductUser;
import com.example.ms_backend.exceptions.NotFoundException;
import com.example.ms_backend.repositories.ProductRepository;
import com.example.ms_backend.repositories.ProductUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductUserRepository productUserRepository;
    private final ProductRepository productRepository;

    public void updateProductAmount(UpdateProductAmountDTO updateProductAmountDTO){
        Optional<ProductUser> productUserEntity = productUserRepository.findProductUserByProductIdAndUserId(updateProductAmountDTO.productId(), updateProductAmountDTO.userId());

        if(productUserEntity.isPresent()){
            Integer checkUpdate = productUserRepository.updateAmount(updateProductAmountDTO.productId(), updateProductAmountDTO.userId(), updateProductAmountDTO.amount());
            log.info("Product amount updated: {}", checkUpdate);
        }
        else {
            ProductUser productUser = new ProductUser();
            productUser.setAmount(updateProductAmountDTO.amount());
            productUser.setUserId(updateProductAmountDTO.userId());
            ProductEntity foundProduct = productRepository.findById(updateProductAmountDTO.productId()).orElseThrow(() -> new NotFoundException("Product not found"));
            productUser.setProduct(foundProduct);
            productUserRepository.save(productUser);
        }
    }

}
