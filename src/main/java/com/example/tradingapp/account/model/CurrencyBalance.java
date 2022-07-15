package com.example.tradingapp.account.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public class CurrencyBalance {

    private String currencyName;
    private BigDecimal availableBalance;

}
