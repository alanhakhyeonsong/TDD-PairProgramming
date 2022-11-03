package com.nhnacademy.gw1.domain;

public enum CurrencyType {
    WON("won"), DOLLAR("dollar"), YEN("yen");

    CurrencyType(String type) {
        this.type = type;
    }

    private final String type;

    public String getType() {
        return type;
    }
}
