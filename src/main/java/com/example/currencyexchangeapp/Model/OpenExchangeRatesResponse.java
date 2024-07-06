package com.example.currencyexchangeapp.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
@Data
public class OpenExchangeRatesResponse {
    @JsonProperty("base")
    private String baseCurrency;

    @JsonProperty("rates")
    private Map<String, Double> rates;
}
