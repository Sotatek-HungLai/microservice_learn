package com.example.ms_product_service.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
public class GetProductsResponseDTO {

    private List<ProductDTO> productList;

    private MetadataPage metadata;
}
