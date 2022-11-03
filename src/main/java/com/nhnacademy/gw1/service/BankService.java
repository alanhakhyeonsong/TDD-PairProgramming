package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.Currency;
import com.nhnacademy.gw1.domain.Dollar;
import com.nhnacademy.gw1.domain.Won;
import com.nhnacademy.gw1.exception.CurrencyTypeMismatchException;
import com.nhnacademy.gw1.exception.InvalidRequestAmountException;
import com.nhnacademy.gw1.exception.RequestAmountOverThenCurrentAmountException;

public class BankService {

    private final ExchangeService exchangeService;

    public BankService(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    public double deposit(Currency currentMoney, Currency depositRequest) {
        checkEqualCurrency(currentMoney, depositRequest);
        checkRequestMoney(depositRequest);

        double resultAmount = currentMoney.getMoney().getAmount() + depositRequest.getMoney().getAmount();
        calculateMoney(currentMoney, resultAmount);

        return currentMoney.getMoney().getAmount();

    }

    public double withdraw(Currency currentMoney, Currency withdrawRequest) {
        checkEqualCurrency(currentMoney, withdrawRequest);
        checkRequestMoney(withdrawRequest);

        if (isOverThanCurrentMoney(currentMoney, withdrawRequest)) {
            throw new RequestAmountOverThenCurrentAmountException(String.valueOf(withdrawRequest.getMoney().getAmount()));
        }

        double resultAmount = currentMoney.getMoney().getAmount() - withdrawRequest.getMoney().getAmount();
        calculateMoney(currentMoney, resultAmount);

        return currentMoney.getMoney().getAmount();
    }

    public Currency exchange(Currency inputCurrency, String wantToChange) {
        checkRequestMoney(inputCurrency);
        Currency result = null;

        result = exchangeService.rateCalculate(inputCurrency, wantToChange);

        return result;
    }

    private void checkRequestMoney(Currency requestMoney) {
        if (requestMoney.getMoney().getAmount() < 0) {
            throw new InvalidRequestAmountException(String.valueOf(requestMoney.getMoney().getAmount()));
        }
    }

    private void checkEqualCurrency(Currency currentMoney, Currency requestMoney) {
        if (!(((currentMoney instanceof Won) && (requestMoney instanceof Won)) || ((currentMoney instanceof Dollar) && (requestMoney instanceof Dollar)))) {
            String message = "From: " + currentMoney.getClass().getSimpleName() + ", To: " + requestMoney.getClass().getSimpleName();
            throw new CurrencyTypeMismatchException(message);
        }
    }

    private void calculateMoney(Currency currentMoney, double amount) {
        currentMoney.getMoney().changeAmount(amount);
    }

    private boolean isOverThanCurrentMoney(Currency currentMoney, Currency withdrawRequest) {
        return currentMoney.getMoney().getAmount() < withdrawRequest.getMoney().getAmount();
    }
}
