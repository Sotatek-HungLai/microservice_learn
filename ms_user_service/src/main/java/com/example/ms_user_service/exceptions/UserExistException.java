package com.example.ms_user_service.exceptions;

public class UserExistException extends RuntimeException {
    public UserExistException() {
        super("User already exists");
    }
}
