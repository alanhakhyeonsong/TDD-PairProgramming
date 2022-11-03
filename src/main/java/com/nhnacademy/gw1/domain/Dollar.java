package com.nhnacademy.gw1.domain;

public class Dollar implements Currency {

    private Money money;

    public Dollar(Money money) {
        this.money = money;
    }

    @Override
    public Money getMoney() {
        money.changeAmount(Double.parseDouble(String.format("%.2f", this.money.getAmount())));
        return money;
    }
}
