package com.example.ms_market.utils;

import com.example.ms_market.dtos.OrderDTO;
import com.example.ms_market.entities.OrderEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderConverter {

    private final ModelMapper modelMapper;

    public OrderDTO convertToDto(OrderEntity orderEntity) {
        return modelMapper.map(orderEntity, OrderDTO.class);
    }

    public OrderEntity convertToEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, OrderEntity.class);
    }
}
