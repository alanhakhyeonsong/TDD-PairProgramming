package com.nhnacademy.gw1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    // SUT
    private Money money;

    private final int INIT_AMOUNT = 1000;

    @BeforeEach
    void setUp() {
        money = new Money(INIT_AMOUNT);
    }

    @Test
    @DisplayName("가지고 있는 돈 불러오기 성공")
    void getAmount() {
        double amount = money.getAmount();

        assertThat(amount).isEqualTo(INIT_AMOUNT);
    }
}