package com.nhnacademy.gw1.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class YenTest {

    // SUT
    private Yen yen;

    final double INPUT_AMOUNT = 2000.5124;

    @BeforeEach
    void setUp() {
        Money money = new Money(INPUT_AMOUNT);
        yen = new Yen(money);
    }

    @Test
    @DisplayName("정수 자리 까지 계산되어 액수가 반환된다.")
    void getMoney() {
        double expectedAmount = 2000;
        double actualAmount = yen.getMoney().getAmount();

        assertThat(actualAmount).isEqualTo(expectedAmount);
        assertThat(INPUT_AMOUNT).isNotEqualTo(actualAmount);
    }
}