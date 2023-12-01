package com.example.ms_market.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GetOrdersResponseDTO {
    private List<OrderDTO> orderList;

    private MetadataPage metadata;
}
