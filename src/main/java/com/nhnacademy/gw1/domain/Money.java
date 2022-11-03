package com.nhnacademy.gw1.domain;

public class Money {

    private double amount;

    public Money(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void changeAmount(double amount) {
        this.amount = amount;
    }
}
