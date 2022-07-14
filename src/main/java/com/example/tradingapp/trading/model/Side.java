package com.example.tradingapp.trading.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Side {
    @JsonProperty("buy")
    BUY,
    @JsonProperty("sell")
    SELL

}

