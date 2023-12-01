package com.example.ms_product_service.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class ProductUserDTO {
    private Long userId;
    private Long amount;
}
