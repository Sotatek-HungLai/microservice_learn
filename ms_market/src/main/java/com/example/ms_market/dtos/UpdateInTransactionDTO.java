package com.example.ms_market.dtos;

public record UpdateInTransactionDTO (Long productId, Long sellerId, Long buyerId, Long amount, Double price){
}
