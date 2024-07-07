package com.example.currencyexchangeapp.Service;

import com.example.currencyexchangeapp.Model.Currency;

import java.util.HashMap;

public interface CurrencyExchangeService {
    Double convert(String baseCurrency , Double baseValue, String targetCurrency);

    HashMap<Currency, Double> getAmountRelativelyToPopularCurrencies(Currency baseCurrency, Double amount);

    HashMap<Currency, Double> getExchangeRatesRelativeToBase(Currency baseCurrency);

    HashMap<Currency, Double> getHistoricalExchange(String data);

    HashMap<Currency, String> getCurrencyInformation();
}
