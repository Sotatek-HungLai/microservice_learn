package com.example.ms_product_service.dtos;

import com.example.ms_product_service.entities.ProductUser;
import lombok.*;

import java.util.Set;

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

    private Set<ProductUserDTO> productUsers;
}
