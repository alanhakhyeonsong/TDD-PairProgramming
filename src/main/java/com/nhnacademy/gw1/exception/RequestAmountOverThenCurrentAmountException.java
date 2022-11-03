package com.nhnacademy.gw1.exception;

public class RequestAmountOverThenCurrentAmountException extends RuntimeException {
    public RequestAmountOverThenCurrentAmountException(String message) {
        super("가진 돈 보다 요청한 금액이 더 많습니다: " + message);
    }
}
