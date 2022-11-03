package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.Currency;

public interface ExchangeService {

    Currency rateCalculate(Currency inputCurrency, String wantToChange);
}
