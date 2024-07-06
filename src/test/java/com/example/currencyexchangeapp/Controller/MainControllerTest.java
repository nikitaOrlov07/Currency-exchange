package com.example.currencyexchangeapp.Controller;

import com.example.currencyexchangeapp.Model.Currency;
import com.example.currencyexchangeapp.Service.CurrencyExchangeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MainController.class)
@ExtendWith(MockitoExtension.class)
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private MainController currencyController;

    @Test
    void testConvert() throws Exception {
        when(currencyExchangeService.convert("USD", 100.0, "EUR")).thenReturn(85.0);

        mockMvc.perform(get("/currencyExchange/convert")
                        .param("fromCurrency", "USD")
                        .param("toCurrency", "EUR")
                        .param("amount", "100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value("USD")) // looks for the fromCurrency field in the root of the JSON object ($ stands for JSON root) and checks that the value of this field is "USD"
                .andExpect(jsonPath("$.toCurrency").value("EUR"))
                .andExpect(jsonPath("$.amount").value(100.0));


        verify(currencyExchangeService).convert("USD", 100.0, "EUR");
    }



    @Test
    void testGetMoneyRelativelyToPopularCurrencies() throws Exception {
        HashMap<Currency, Double> result = new HashMap<>();
        result.put(Currency.USD, 100.0);
        result.put(Currency.EUR, 85.0);
        result.put(Currency.UAH, 2750.0);
        result.put(Currency.RUB, 7350.0);

        when(currencyExchangeService.getAmountRelativelyToPopularCurrencies(Currency.USD, 100.0)).thenReturn(result);

        mockMvc.perform(get("/currencyExchange/compare")
                        .param("fromCurrency", "USD")
                        .param("amount", "100.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.USD").value(100.0))
                .andExpect(jsonPath("$.EUR").value(85.0))
                .andExpect(jsonPath("$.UAH").value(2750.0))
                .andExpect(jsonPath("$.RUB").value(7350.0));

        verify(currencyExchangeService).getAmountRelativelyToPopularCurrencies(Currency.USD, 100.0);
    }

    @Test
    void testGetExchangeRate() throws Exception {
        HashMap<Currency, Double> result = new HashMap<>();
        result.put(Currency.EUR, 0.85);
        result.put(Currency.GBP, 0.75);
        result.put(Currency.JPY, 110.0);

        when(currencyExchangeService.getExchangeRatesRelativeToBase(Currency.USD)).thenReturn(result);

        mockMvc.perform(get("/currencyExchange/exchangeRate")
                        .param("currency", "USD"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.EUR").value(0.85))
                .andExpect(jsonPath("$.GBP").value(0.75))
                .andExpect(jsonPath("$.JPY").value(110.0));

        verify(currencyExchangeService).getExchangeRatesRelativeToBase(Currency.USD);
    }
}
