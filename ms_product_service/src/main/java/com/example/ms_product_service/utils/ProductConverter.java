package com.example.ms_product_service.utils;

import com.example.ms_product_service.dtos.ProductDTO;
import com.example.ms_product_service.entities.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final ModelMapper modelMapper;

    public ProductDTO convertToDto(ProductEntity productEntity) {
        return modelMapper.map(productEntity, ProductDTO.class);
    }

    public ProductEntity convertToEntity(ProductDTO productDTO) {
        return modelMapper.map(productDTO, ProductEntity.class);
    }
}
