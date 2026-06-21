package com.limerencer.demo_backend.identity.exception;

public class UserAlreadyExistsException extends IdentityException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}

