package com.example.currencyexchangeapp.DTO;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

// Dto for response STRING - STRING
@Data
public class OpenExchangeRateInformationResponse {
    private Map<String, String> information = new HashMap<>();
    @JsonAnySetter // annotation , used  for JSON deserialisation when you need to dynamically add properties to a Java object that are not present in the DTO class
    public void setInformation(String key, String value) {
        information.put(key, value);
    }
}
