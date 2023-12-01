package com.example.ms_backend.dtos;

public record UpdateInTransactionDTO(Long productId, Long sellerId, Long buyerId, Long amount, Double price){
}
