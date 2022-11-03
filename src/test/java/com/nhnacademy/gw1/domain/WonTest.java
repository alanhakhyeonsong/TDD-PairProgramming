package com.nhnacademy.gw1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WonTest {

    // SUT
    private Won won;

    final double INPUT_AMOUNT = 2000.235098901358;

    @BeforeEach
    void setUp() {
        Money money = new Money(INPUT_AMOUNT);
        won = new Won(money);
    }

    @Test
    @DisplayName("정수 자리 까지 계산되어 액수가 반환된다.")
    void getMoney() {
        double expectedAmount = 2000;
        double actualAmount = won.getMoney().getAmount();

        assertThat(actualAmount).isEqualTo(expectedAmount);
        assertThat(INPUT_AMOUNT).isNotEqualTo(actualAmount);
    }
}