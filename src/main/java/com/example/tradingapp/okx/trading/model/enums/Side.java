package com.example.tradingapp.okx.trading.model.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Side {
    @JsonProperty("buy")
    BUY,
    @JsonProperty("sell")
    SELL,
    @JsonProperty("zero")
    ZERO

}

