package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WonExchangeServiceServiceTest {

    private WonExchangeServiceService service;

    @BeforeEach
    void setUp() {
        service = new WonExchangeServiceService();
    }

    @Test
    @DisplayName("원 -> 달러 환전")
    void rateCalculate_won_to_dollar() {
        // 1005원 -> 1.01달러 -> 0.91달러
        double inputAmount = 1005;
        Currency won = new Won(new Money(inputAmount));

        Currency dollar = service.rateCalculate(won, "dollar");

        assertThat(dollar).isInstanceOf(Dollar.class);
        double expectedDollarAmount = 0.91;
        assertThat(dollar.getMoney().getAmount()).isEqualTo(expectedDollarAmount);
    }

    @Test
    @DisplayName("원 -> 엔 환전")
    void rateCalculate_won_to_yen() {
        // 1000원 -> 100엔 -> 90엔
        double inputAmount = 1000;
        Currency won = new Won(new Money(inputAmount));


        Currency yen = service.rateCalculate(won, "yen");

        assertThat(yen).isInstanceOf(Yen.class);
        double expectedYenAmount = 90.0;
        assertThat(yen.getMoney().getAmount()).isEqualTo(expectedYenAmount);
    }
}