package com.nhnacademy.gw1.exception;

public class InvalidRequestAmountException extends RuntimeException {
    public InvalidRequestAmountException(String message) {
        super("Request amount is minus: " + message);
    }
}
