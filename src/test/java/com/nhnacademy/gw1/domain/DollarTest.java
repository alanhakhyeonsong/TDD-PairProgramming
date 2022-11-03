package com.nhnacademy.gw1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DollarTest {

    // SUT
    Dollar dollar;

    private final double INIT_AMOUNT = 2.13452;

    @BeforeEach
    void setUp() {
        Money money = new Money(INIT_AMOUNT);
        dollar = new Dollar(money);
    }

    @Test
    @DisplayName("소숫점 아래 2자리 까지 계산되어 액수가 반환된다.")
    void getMoney() {
        double expectedAmount = 2.13;
        double actualAmount = dollar.getMoney().getAmount();

        assertThat(actualAmount).isEqualTo(expectedAmount);
        assertThat(INIT_AMOUNT).isNotEqualTo(actualAmount);
    }
}