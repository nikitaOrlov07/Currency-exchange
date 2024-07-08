package com.example.currencyexchangeapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Value {

    private Currency fromCurrency;
    private Currency toCurrency;
    private Double amount;
    private  Double res;

}
