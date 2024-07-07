package com.example.currencyexchangeapp.Service;

import com.example.currencyexchangeapp.Model.Currency;
import com.example.currencyexchangeapp.DTO.OpenExchangeRatesResponse;
import com.example.currencyexchangeapp.Service.impl.CurrentExchangeServiceimpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CurrentExchangeServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CurrentExchangeServiceimpl exchangeRateService;

    @BeforeEach
    void setUp() {
        exchangeRateService.setApiKey("test-api-key");
    }

    @Test
    void CurrentExchangeService_GetExchangeRate() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.85);
        response.setRates(rates);

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(OpenExchangeRatesResponse.class))).thenReturn(response);

        Double rate = exchangeRateService.getExchangeRate("USD", "EUR");
        Assertions.assertEquals(0.85, rate, 0.001); // thirs argument -> delta -> available range of difference
    }
    @Test
    void testGetAmountRelativelyToPopularCurrencies() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.85);
        rates.put("UAH", 27.5);
        rates.put("RUB", 73.5);
        response.setRates(rates);

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(OpenExchangeRatesResponse.class))).thenReturn(response); // getForObject("url", class type)

        HashMap<Currency, Double> result = exchangeRateService.getAmountRelativelyToPopularCurrencies(Currency.EUR, 100.0);

        Assertions.assertEquals(117.65, result.get(Currency.USD), 0.01);
        Assertions.assertEquals(3235.29, result.get(Currency.UAH), 0.01);
        Assertions.assertEquals(100.0, result.get(Currency.EUR), 0.01);
        Assertions.assertEquals(8647.06, result.get(Currency.RUB), 0.01);
    }

    @Test
    void testGetExchangeRatesRelativeToBase() {
        OpenExchangeRatesResponse response = new OpenExchangeRatesResponse();
        Map<String, Double> rates = new HashMap<>();
        rates.put("USD", 1.0);
        rates.put("EUR", 0.85);
        rates.put("GBP", 0.75);
        rates.put("JPY", 110.0);
        response.setRates(rates);

        when(restTemplate.getForObject(Mockito.anyString(), Mockito.eq(OpenExchangeRatesResponse.class))).thenReturn(response);

        HashMap<Currency, Double> result = exchangeRateService.getExchangeRatesRelativeToBase(Currency.EUR);

        Assertions.assertFalse(result.containsKey(Currency.EUR));
        Assertions. assertEquals(1.0 / 0.85, result.get(Currency.USD), 0.001);
        Assertions.assertEquals(0.75 / 0.85, result.get(Currency.GBP), 0.001);
        Assertions. assertEquals(110.0 / 0.85, result.get(Currency.JPY), 0.001);
    }

}
