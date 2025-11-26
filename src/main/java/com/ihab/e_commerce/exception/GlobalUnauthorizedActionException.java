package com.ihab.e_commerce.exception;

public class GlobalUnauthorizedActionException extends RuntimeException {
    public GlobalUnauthorizedActionException(String message) {
        super(message);
    }
}
