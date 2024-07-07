package com.example.currencyexchangeapp.Service.impl;

import com.example.currencyexchangeapp.DTO.OpenExchangeRateInformationResponse;
import com.example.currencyexchangeapp.Model.Currency;
import com.example.currencyexchangeapp.DTO.OpenExchangeRatesResponse;
import com.example.currencyexchangeapp.Service.CurrencyExchangeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class CurrentExchangeServiceimpl implements CurrencyExchangeService {
    @Value("#{@base64DecodeConverter.convert('${api.key}')}") // decrypt key value from application.properties file
    private String apiKey;

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Double convert(String baseCurrency, Double baseValue, String targetCurrency) {
        Double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);
        return exchangeRate * baseValue;
    }

    public Double getExchangeRate(String baseCurrency, String targetCurrency) {
        String url = UriComponentsBuilder.fromHttpUrl("https://openexchangerates.org/api/latest.json")
                .queryParam("app_id", apiKey)
                .toUriString();

        OpenExchangeRatesResponse response = restTemplate.getForObject(url, OpenExchangeRatesResponse.class);

        if (response != null && response.getRates() != null) {
            Double baseToUSD = response.getRates().get(baseCurrency);
            Double targetToUSD = response.getRates().get(targetCurrency);

            if (baseToUSD != null && targetToUSD != null) {
                if (baseCurrency.equals("USD")) {
                    return targetToUSD;
                } else if (targetCurrency.equals("USD")) {
                    return 1 / baseToUSD;
                } else {
                    return targetToUSD / baseToUSD;
                }
            }
        }
        return null;
    }
    @Override
    public HashMap<Currency, Double> getAmountRelativelyToPopularCurrencies(Currency baseCurrency, Double amount) {
        HashMap<Currency, Double> result = new HashMap<>();

        String url = UriComponentsBuilder.fromHttpUrl("https://openexchangerates.org/api/latest.json")
                .queryParam("app_id", apiKey)
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

    @Override
    public HashMap<Currency, Double> getHistoricalExchange(String date) {
        HashMap<Currency, Double> result = new HashMap<>();
        Currency baseCurrency = Currency.USD;

        String url = "https://openexchangerates.org/api/historical/" + date + ".json?app_id=" + apiKey + "&base="+baseCurrency;

        OpenExchangeRatesResponse response = restTemplate.getForObject(url, OpenExchangeRatesResponse.class);

        if (response != null && response.getRates() != null) {
            Map<String, Double> rates = response.getRates();
            double baseRate = rates.getOrDefault(baseCurrency.name(), 1.0);

            for (Currency currency : Currency.values()) {
                    double rate = rates.getOrDefault(currency.name(), 0.0);
                    double relativeRate = rate / baseRate;
                    result.put(currency, relativeRate);
            }
        }

        return result;
    }

    @Override
    public HashMap<Currency, String> getCurrencyInformation() {
        HashMap<Currency,String> result = new HashMap<Currency,String>();
        String url ="https://openexchangerates.org/api/currencies.json";
        log.info("Currency get information service method is working");
        OpenExchangeRateInformationResponse response = restTemplate.getForObject(url, OpenExchangeRateInformationResponse.class);
        if (response != null && response.getInformation() != null) {
            for (Map.Entry<String, String> entry : response.getInformation().entrySet()) {
                try {
                    Currency currency = Currency.valueOf(entry.getKey());
                    result.put(currency, entry.getValue());
                } catch (IllegalArgumentException e) {
                    log.error("Invalid currency code: " + entry.getKey());
                }
            }
        }

        return result;
    }

}
