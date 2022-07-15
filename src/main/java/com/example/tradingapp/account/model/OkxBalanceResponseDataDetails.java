package com.example.tradingapp.account.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OkxBalanceResponseDataDetails {

    @JsonProperty("availBal")
    private BigDecimal availableBalance;
    @JsonProperty("ccy")
    private String currencyName;

}
