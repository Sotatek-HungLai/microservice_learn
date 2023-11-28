package com.example.ms_product_service.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("Product not found");
    }
}
