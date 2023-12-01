package com.example.ms_product_service.exceptions;

public class ProductUserNotFoundException extends RuntimeException{
    public ProductUserNotFoundException() {
        super("Product  of user not found");
    }
}
