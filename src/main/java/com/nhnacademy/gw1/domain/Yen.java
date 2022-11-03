package com.nhnacademy.gw1.domain;

public class Yen implements Currency {

    private Money money;

    public Yen(Money money) {
        this.money = money;
    }

    @Override
    public Money getMoney() {
        money.changeAmount(Math.floor(this.money.getAmount()));
        return money;
    }
}
