package com.nhnacademy.gw1.exception;

public class CurrencyTypeMismatchException extends RuntimeException{
    public CurrencyTypeMismatchException(String message) {
        super("TypeMismatch: " + message);
    }
}
