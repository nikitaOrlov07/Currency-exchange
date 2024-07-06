package com.example.currencyexchangeapp.Controller;

import com.example.currencyexchangeapp.Model.Currency;
import com.example.currencyexchangeapp.Model.Value;
import com.example.currencyexchangeapp.Service.CurrencyExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/currencyExchange")
public class MainController {

    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    public MainController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }

    @GetMapping("/convert")
    public Value convert(@RequestParam Currency fromCurrency, @RequestParam Currency toCurrency, @RequestParam Double amount)
    {
       Double targetAmount = currencyExchangeService.convert(fromCurrency.toString(), amount, toCurrency.toString());
       return new Value(fromCurrency,toCurrency,amount,targetAmount);
    }
    @GetMapping("/compare")
    public HashMap<Currency,Double> getMoneyRelativelyToPopularCurrencies(@RequestParam Currency fromCurrency , @RequestParam Double amount) // EUR , USD, UAH,RUB
    {
        return currencyExchangeService.getAmountRelativelyToPopularCurrencies(fromCurrency,amount) ;
    }
    @GetMapping("/exchangeRate")
    public HashMap<Currency,Double> getExchangeRate(@RequestParam Currency currency)
    {
        return currencyExchangeService.getExchangeRatesRelativeToBase(currency);
    }
}
