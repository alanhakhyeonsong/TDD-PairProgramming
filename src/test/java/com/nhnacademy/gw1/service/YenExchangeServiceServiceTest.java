package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class YenExchangeServiceServiceTest {

    private YenExchangeServiceService service;

    @BeforeEach
    void setUp() {
        service = new YenExchangeServiceService();
    }

    @Test
    @DisplayName("엔 -> 달러 환전")
    void rateCalculate_yen_to_dollar() {
        // 100엔 -> 1달러 -> 0.9달러
        int inputAmount = 100;
        Currency yen = new Yen(new Money(inputAmount));

        Currency dollar = service.rateCalculate(yen, "dollar");

        double expectedDollarAmount = 0.9;

        assertThat(dollar).isInstanceOf(Dollar.class);
        assertThat(dollar.getMoney().getAmount()).isEqualTo(expectedDollarAmount);
    }

    @Test
    @DisplayName("엔 -> 원 환전")
    void rateCalculate_yen_to_won() {
        // 100엔 -> 1000원 -> 900원
        int inputAmount = 100;
        Currency yen = new Yen(new Money(inputAmount));

        Currency won = service.rateCalculate(yen, "won");

        assertThat(won).isInstanceOf(Won.class);
        int expectedWonAmount = 900;
        assertThat(won.getMoney().getAmount()).isEqualTo(expectedWonAmount);
    }
}