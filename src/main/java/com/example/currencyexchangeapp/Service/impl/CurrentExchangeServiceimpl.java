package com.example.currencyexchangeapp.Service.impl;

import com.example.currencyexchangeapp.Model.Currency;
import com.example.currencyexchangeapp.Model.OpenExchangeRatesResponse;
import com.example.currencyexchangeapp.Service.CurrencyExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CurrentExchangeServiceimpl implements CurrencyExchangeService {
    private String apiKey = "c313a02b21bf4d6d9a8c099ecfe57a6c";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Double convert(String baseCurrency , Double baseValue, String targetCurrency)
    {
        Double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);

        return exchangeRate * baseValue;
    }

    public Double getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = UriComponentsBuilder.fromHttpUrl("https://openexchangerates.org/api/latest.json")
                .queryParam("app_id", apiKey)
                .queryParam("base", baseCurrency)
                .toUriString();

        OpenExchangeRatesResponse response = restTemplate.getForObject(url, OpenExchangeRatesResponse.class); // is used to deserialise the JSON response that the Open Exchange Rates API returns using the restTemplate library

        if (response != null && response.getRates() != null) {
            Double baseToUSD = response.getRates().get("USD");
            Double targetToUSD = response.getRates().get(targetCurrency);
            if (baseToUSD != null && targetToUSD != null) {
                return targetToUSD / baseToUSD;
            }
        }
        return null;
    }
    @Override
    public HashMap<Currency, Double> getAmountRelativelyToPopularCurrencies(Currency baseCurrency, Double amount) {
        HashMap<Currency, Double> result = new HashMap<>();

        String url = UriComponentsBuilder.fromHttpUrl("https://openexchangerates.org/api/latest.json")
                .queryParam("app_id", apiKey)
                .queryParam("base", "USD") // Always request rates relative to USD
                .toUriString();

        OpenExchangeRatesResponse response = restTemplate.getForObject(url, OpenExchangeRatesResponse.class);

        if (response != null && response.getRates() != null) {
            Map<String, Double> rates = response.getRates();

            // Get the rate for the base currency relative to USD
            double baseRate = rates.getOrDefault(baseCurrency.name(), 1.0);

            // Calculate and add exchanged amounts for USD, UAH, EUR, RUB
            result.put(Currency.USD, amount * (1 / baseRate));
            result.put(Currency.UAH, amount * (rates.getOrDefault("UAH", 0.0) / baseRate));
            result.put(Currency.EUR, amount * (rates.getOrDefault("EUR", 0.0) / baseRate));
            result.put(Currency.RUB, amount * (rates.getOrDefault("RUB", 0.0) / baseRate));
        }

        return result;
    }
    @Override
    public HashMap<Currency, Double> getExchangeRatesRelativeToBase(Currency baseCurrency) {
        HashMap<Currency, Double> result = new HashMap<>();

        String url = UriComponentsBuilder.fromHttpUrl("https://openexchangerates.org/api/latest.json")
                .queryParam("app_id", apiKey)
                .queryParam("base", "USD") // Always request rates relative to USD
                .toUriString();

        OpenExchangeRatesResponse response = restTemplate.getForObject(url, OpenExchangeRatesResponse.class);

        if (response != null && response.getRates() != null) {
            Map<String, Double> rates = response.getRates();
            double baseRate = rates.getOrDefault(baseCurrency.name(), 1.0);

            for (Currency currency : Currency.values()) {
                if (currency != baseCurrency) {
                    double rate = rates.getOrDefault(currency.name(), 0.0);
                    double relativeRate = rate / baseRate;
                    result.put(currency, relativeRate);
                }
            }
        }

        return result;
    }

}
