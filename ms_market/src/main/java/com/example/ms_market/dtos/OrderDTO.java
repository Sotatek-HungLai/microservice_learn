package com.example.ms_market.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Long id;

    private Long amount;

    private Double price;

    private Long productId;


}
