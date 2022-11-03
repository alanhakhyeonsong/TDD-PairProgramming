package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DollarExchangeServiceServiceTest {

    private DollarExchangeServiceService service;

    @BeforeEach
    void setUp() {
        service = new DollarExchangeServiceService();
    }

    @Test
    @DisplayName("달러 -> 엔 환전")
    void rateCalculate_dollar_to_yen() {
        // 1달러 -> 100엔 -> 90엔
        double inputAmount = 1.0;
        Currency dollar = new Dollar(new Money(inputAmount));

        Currency yen = service.rateCalculate(dollar, "yen");

        assertThat(yen).isInstanceOf(Yen.class);
        assertThat(yen.getMoney().getAmount()).isEqualTo(90);
    }

    @Test
    @DisplayName("달러 -> 원 환전")
    void rateCalculate_dollar_to_won() {
        // 1.005달러 -> 1010원 -> 909.0원
        double inputAmount = 1.005;
        Currency dollar = new Dollar(new Money(inputAmount));

        Currency won = service.rateCalculate(dollar, "won");

        double expectedWonAmount = 909.0;

        assertThat(won).isInstanceOf(Won.class);
        assertThat(won.getMoney().getAmount()).isEqualTo(expectedWonAmount);
    }
}