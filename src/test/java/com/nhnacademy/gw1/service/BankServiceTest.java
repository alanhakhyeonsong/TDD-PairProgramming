package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;
import com.nhnacademy.gw1.exception.CurrencyTypeMismatchException;
import com.nhnacademy.gw1.exception.InvalidRequestAmountException;
import com.nhnacademy.gw1.exception.RequestAmountOverThenCurrentAmountException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class BankServiceTest {

    // SUT
    private BankService bankService;

    // DOC
    private ExchangeService exchangeService;

    @BeforeEach
    void setUp() {
        exchangeService = mock(ExchangeService.class);
        bankService = new BankService(exchangeService);
    }

    @Test
    @DisplayName("서로 다른 통화일 경우 deposit 실패")
    void inputMismatchCurrencyType_deposit_fail_throwCurrencyTypeMismatchException() {
        Currency c1 = new Won(new Money(1000));
        Currency c2 = new Dollar(new Money(2000));

        assertThatThrownBy(() -> bankService.deposit(c1, c2))
                .isInstanceOf(CurrencyTypeMismatchException.class)
                .hasMessageContainingAll("TypeMismatch: ", c1.getClass().getSimpleName(), c2.getClass().getSimpleName());
    }

    @Test
    @DisplayName("입금에서 요청하는 금액이 음수일 경우 실패")
    void deposit_if_RequestValue_is_minus_fail_throwInvalidRequestAmountException() {
        Currency currentMoney = new Won(new Money(1000));
        Currency depositRequest = new Won(new Money(-2000));

        assertThatThrownBy(() -> bankService.deposit(currentMoney, depositRequest))
                .isInstanceOf(InvalidRequestAmountException.class)
                .hasMessageContainingAll("Request amount is minus: " + depositRequest.getMoney().getAmount());
    }

    @Test
    @DisplayName("서로 같은 통화(원)일 경우 deposit 성공")
    void deposit_success_when_won_to_won() {
        Currency currentMoney = new Won(new Money(1000));
        Currency depositRequest = new Won(new Money(2000));

        double depositResult = bankService.deposit(currentMoney, depositRequest);

        assertThat(currentMoney.getMoney().getAmount()).isEqualTo(depositResult);
    }

    @Test
    @DisplayName("서로 같은 통화(달러)일 경우 deposit 성공")
    void deposit_success_when_dollar_to_dollar() {
        Currency currentMoney = new Dollar(new Money(10.004));
        Currency depositRequest = new Dollar(new Money(10.005));

        double depositResult = bankService.deposit(currentMoney, depositRequest);

        assertThat(currentMoney.getMoney().getAmount()).isEqualTo(depositResult);
    }

    @Test
    @DisplayName("출금에서 요청하는 금액이 음수일 경우 실패")
    void withdraw_if_RequestValue_is_minus_fail_throwInvalidRequestAmountException() {
        Currency currentMoney = new Won(new Money(1000));
        Currency withdrawRequest = new Won(new Money(-2000));

        assertThatThrownBy(() -> bankService.withdraw(currentMoney, withdrawRequest))
                .isInstanceOf(InvalidRequestAmountException.class)
                .hasMessageContainingAll("Request amount is minus: ");
    }

    @Test
    @DisplayName("서로 다른 통화일 경우 withdraw 실패")
    void inputMismatchCurrencyType_withdraw_fail_throwCurrencyTypeMismatchException() {
        Currency currentMoney = new Won(new Money(1000));
        Currency withdrawRequest = new Dollar(new Money(2000));

        assertThatThrownBy(() -> bankService.withdraw(currentMoney, withdrawRequest))
                .isInstanceOf(CurrencyTypeMismatchException.class)
                .hasMessageContainingAll("TypeMismatch: ");
    }

    @Test
    @DisplayName("가진 돈 보다 더 많은 금액을 출금할 경우 에러")
    void requestMoreThenCurrentMoney_fail_withdraw_throwRequestAmountOverThenCurrentAmountException() {
        Currency currentMoney = new Won(new Money(1000));
        Currency withdrawRequest = new Won(new Money(2000));

        assertThatThrownBy(() -> bankService.withdraw(currentMoney, withdrawRequest))
                .isInstanceOf(RequestAmountOverThenCurrentAmountException.class)
                .hasMessageContainingAll("가진 돈 보다 요청한 금액이 더 많습니다: ");
    }

    @Test
    @DisplayName("서로 같은 통화(원)일 경우 withdraw 성공")
    void withdraw_success_when_won_to_won() {
        Currency currentMoney = new Won(new Money(2000));
        Currency withdrawRequest = new Won(new Money(1000));

        double depositResult = bankService.withdraw(currentMoney, withdrawRequest);

        assertThat(currentMoney.getMoney().getAmount()).isEqualTo(depositResult);
    }

    @Test
    @DisplayName("서로 같은 통화(달러)일 경우 withdraw 성공")
    void withdraw_success_when_dollar_to_dollar() {
        Currency currentMoney = new Dollar(new Money(20.00));
        Currency withdrawRequest = new Dollar(new Money(10.01));

        double depositResult = bankService.withdraw(currentMoney, withdrawRequest);

        assertThat(currentMoney.getMoney().getAmount()).isEqualTo(depositResult);
    }

    @Test
    @DisplayName("요청하는 값이 음수일 경우 Exception")
    void exchange_if_RequestValue_is_minus_fail_throwInvalidRequestAmountException() {
        Currency won = new Won((new Money(-1000)));

        assertThatThrownBy(() -> bankService.exchange(won, "dollar"))
                .isInstanceOf(InvalidRequestAmountException.class)
                .hasMessageContainingAll("Request amount is minus: ");
    }

    @Test
    @DisplayName("원 -> 달러 변환시 계산 성공")
    void exchangeFromWonToDollar_success(){
        // 1005원 -> 1.01달러 -> 0.91달러
        double inputAmount = 1005;
        Currency won = new Won(new Money(inputAmount));

        double expectedAmount = 0.91; // 수수료 계산 후 금액

        Dollar exchangeResult = new Dollar(new Money(expectedAmount));
        when(exchangeService.rateCalculate(won, "dollar")).thenReturn(exchangeResult);

        Currency dollar = bankService.exchange(won, "dollar");

        assertThat(dollar).isInstanceOf(Dollar.class);
        assertThat(dollar.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(won, "dollar");
    }

    @Test
    @DisplayName("원 -> 엔 변환 시 계산 성공")
    void exchangeFromWonToYen() {
        // 1000원 -> 100엔 -> 90엔
        int inputAmount = 1000;
        Currency won = new Won(new Money(inputAmount));

        double expectedAmount = 90.0; // 수수료 계산 후 금액

        Yen exchangeResult = new Yen(new Money(expectedAmount));
        when(exchangeService.rateCalculate(won, "yen")).thenReturn(exchangeResult);

        Currency yen = bankService.exchange(won, "yen");

        assertThat(yen).isInstanceOf(Yen.class);
        assertThat(yen.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(won, "yen");
    }

    @Test
    @DisplayName("달러 -> 원 변환 시 계산 성공")
    void exchangeFromDollarToWon_success(){
        // 1.005달러 -> 1010원 -> 909.0원
        double inputAmount = 1.005;
        Currency dollar = new Dollar(new Money(inputAmount));

        double expectedAmount = 909.0; // 수수료 계산 후 금액
        Won exchangeResult = new Won(new Money(expectedAmount));
        when(exchangeService.rateCalculate(dollar, "won")).thenReturn(exchangeResult);

        Currency won = bankService.exchange(dollar, "won");

        assertThat(won).isInstanceOf(Won.class);
        assertThat(won.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(dollar, "won");
    }

    @Test
    @DisplayName("달러 -> 엔 변환 시 계산 성공")
    void exchangeFromDollarToYen() {
        double inputAmount = 1.0;
        Currency dollar = new Dollar(new Money(inputAmount));
        // 1달러 -> 100엔 -> 90엔

        double changedYen = 100.0; // 환전된 금액
        double expectedAmount = 90.0; // 수수료 계산 후 금액
        Yen exchangeResult = new Yen(new Money(expectedAmount));
        when(exchangeService.rateCalculate(dollar, "yen")).thenReturn(exchangeResult);

        Currency yen = bankService.exchange(dollar, "yen");

        assertThat(yen).isInstanceOf(Yen.class);
        assertThat(yen.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(dollar, "yen");
    }

    @Test
    @DisplayName("엔 -> 달러 변환 시 계산 성공")
    void exchangeFromYenToDollar() {
        Currency yen = new Yen(new Money(100));
        // 100엔 -> 1달러 -> 0.9달러

        double expectedAmount = 0.9; // 수수료 계산 후 금액

        Dollar exchangeResult = new Dollar(new Money(expectedAmount));
        when(exchangeService.rateCalculate(yen, "dollar")).thenReturn(exchangeResult);

        Currency dollar = bankService.exchange(yen, "dollar");

        assertThat(dollar).isInstanceOf(Dollar.class);
        assertThat(dollar.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(yen, "dollar");
    }

    @Test
    @DisplayName("엔 -> 원 변환 시 계산 성공")
    void exchangeFromYenToWon() {
        Currency yen = new Yen(new Money(10));
        // 10엔 -> 100원 -> 90원

        double expectedAmount = 90.0; // 수수료 계산 후 금액

        Won exchangeResult = new Won(new Money(expectedAmount));
        when(exchangeService.rateCalculate(yen, "won")).thenReturn(exchangeResult);

        Currency won = bankService.exchange(yen, "won");

        assertThat(won).isInstanceOf(Won.class);
        assertThat(won.getMoney().getAmount()).isEqualTo(expectedAmount);

        verify(exchangeService, times(1)).rateCalculate(yen, "won");
    }

}