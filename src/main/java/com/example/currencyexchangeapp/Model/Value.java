package com.example.currencyexchangeapp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Value {

    @Enumerated(EnumType.STRING) // in database will be stored as String
    @Column(nullable = false)
    private Currency fromCurrency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency toCurrency;

    @Column(nullable = false)
    private Double amount;
    @Column(nullable = false)
    private  Double res;

}
