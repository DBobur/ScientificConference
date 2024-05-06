package com.example.scientificconference.exception;

public class EmailAlreadyExistsExceptions extends RuntimeException {
    public EmailAlreadyExistsExceptions(String email) {
        super("Email already exists: " + email);
    }
}
