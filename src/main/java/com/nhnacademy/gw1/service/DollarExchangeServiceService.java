package com.nhnacademy.gw1.service;

import com.nhnacademy.gw1.domain.*;

import static com.nhnacademy.gw1.domain.CurrencyType.WON;
import static com.nhnacademy.gw1.domain.CurrencyType.YEN;

public class DollarExchangeServiceService implements ExchangeService {

    public static final long EXCHANGE_RATE = 10;

    @Override
    public Currency rateCalculate(Currency inputCurrency, String wantToChange) {
        Currency result = null;

        // dollar -> yen
        if (inputCurrency instanceof Dollar && wantToChange.equals(YEN.getType())) {
            Yen yen = new Yen(new Money(inputCurrency.getMoney().getAmount() * 100));

            double afterCalculateExchangeFee = getAfterCalculateExchangeFee(yen.getMoney());

            yen.getMoney().changeAmount(afterCalculateExchangeFee);
            result = yen;
        }

        // dollar -> won
        if (inputCurrency instanceof Dollar && wantToChange.equals(WON.getType())) {
            Won won = new Won(new Money(inputCurrency.getMoney().getAmount() * 1000));

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
