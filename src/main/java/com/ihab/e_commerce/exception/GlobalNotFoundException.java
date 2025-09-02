package com.ihab.e_commerce.exception;

public class GlobalNotFoundException extends RuntimeException {
    public GlobalNotFoundException(String message) {
        super(message);
    }
}
