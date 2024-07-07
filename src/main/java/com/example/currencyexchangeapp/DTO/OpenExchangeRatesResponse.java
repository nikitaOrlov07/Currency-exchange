package com.example.currencyexchangeapp.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;
// Dto for response STRING - Double
@Data
public class OpenExchangeRatesResponse {
    @JsonProperty("base")
    private String baseCurrency;

    @JsonProperty("rates")
    private Map<String, Double> rates;
}
