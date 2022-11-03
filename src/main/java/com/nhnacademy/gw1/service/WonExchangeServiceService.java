package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;

import static com.nhnacademy.gw1.domain.CurrencyType.DOLLAR;
import static com.nhnacademy.gw1.domain.CurrencyType.YEN;

public class WonExchangeServiceService implements ExchangeService {

    public static final long EXCHANGE_RATE = 10;

    @Override
    public Currency rateCalculate(Currency inputCurrency, String wantToChange) {
        Currency result = null;

        // won -> dollar
        if (inputCurrency instanceof Won && wantToChange.equals(DOLLAR.getType())) {
            Dollar dollar = new Dollar(new Money(inputCurrency.getMoney().getAmount()));
            String roundResult = String.format("%.2f", dollar.getMoney().getAmount() / 1000);

            double parseDouble = Double.parseDouble(roundResult);
            double afterCalculateExchangeFee = Math.round(getAfterCalculateExchangeFee(parseDouble) * 100) / 100.0;

            dollar.getMoney().changeAmount(afterCalculateExchangeFee);
            result = dollar;
        }

        // won -> yen
        if (inputCurrency instanceof Won && wantToChange.equals(YEN.getType())) {
            Yen yen = new Yen(new Money(inputCurrency.getMoney().getAmount() / 10));

            double afterCalculateExchangeFee = getAfterCalculateExchangeFee(yen.getMoney().getAmount());
            yen.getMoney().changeAmount(afterCalculateExchangeFee);

            result = yen;
        }

        return result;
    }

    private double getAfterCalculateExchangeFee(double amount) {
        return amount * ((100.0 - EXCHANGE_RATE) / 100.0);
    }
}
