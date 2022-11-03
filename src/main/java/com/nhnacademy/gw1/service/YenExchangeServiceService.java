package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;

import static com.nhnacademy.gw1.domain.CurrencyType.DOLLAR;
import static com.nhnacademy.gw1.domain.CurrencyType.WON;

public class YenExchangeServiceService implements ExchangeService {

    public static final long EXCHANGE_RATE = 10;

    @Override
    public Currency rateCalculate(Currency inputCurrency, String wantToChange) {
        Currency result = null;

        // yen -> dollar
        if (inputCurrency instanceof Yen && wantToChange.equals(DOLLAR.getType())) {
            Dollar dollar = new Dollar(new Money(inputCurrency.getMoney().getAmount() / 100));

            double afterCalculateExchangeFee = getAfterCalculateExchangeFee(dollar.getMoney());

            dollar.getMoney().changeAmount(afterCalculateExchangeFee);
            result = dollar;
        }

        // yen -> won
        if (inputCurrency instanceof Yen && wantToChange.equals(WON.getType())) {
            Won won = new Won(new Money(inputCurrency.getMoney().getAmount() * 10));

            double afterCalculateExchangeFee = getAfterCalculateExchangeFee(won.getMoney());

            won.getMoney().changeAmount(afterCalculateExchangeFee);
            result = won;
        }

        return result;
    }

    private double getAfterCalculateExchangeFee(Money money) {
        return money.getAmount() * ((100.0 - EXCHANGE_RATE) / 100.0);
    }
}
