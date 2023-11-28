package com.example.ms_product_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ProductDTO {

    private Long id;

    private String name;

    private String description;

    private String imagePath;

    private Long userId;

    private Long amount;
}
